import ktast.ast.Node
import ktast.ast.Visitor
import ktast.ast.Writer
import ktcodeshift.*
import org.jetbrains.kotlin.util.capitalizeDecapitalize.decapitalizeSmart
import java.nio.charset.StandardCharsets

transform { fileInfo ->
    val stringBuilder = StringBuilder()
    Api
        .parse(fileInfo.source)
        .also { ctx ->
            val nestedNames = mutableListOf<String>()
            val fqNames = mutableSetOf<List<String>>()

            object : Visitor() {
                override fun visit(v: Node, parent: Node?) {
                    if (v is Node.Declaration.Class) {
                        val name = v.name?.name.orEmpty()
                        nestedNames.add(name)

                        fqNames.add(nestedNames.toList())

                        super.visit(v, parent)
                        nestedNames.removeLast()
                    } else {
                        super.visit(v, parent)
                    }
                }
            }.visit(ctx.fileNode)

            println(fqNames)
            println("-".repeat(40))
            stringBuilder.appendLine("import ktast.ast.Node")

            fun toFqNameType(type: Node.Type.Simple, nestedNames: List<String>): Node.Type.Simple {

                // e.g. Make List<Expression> to List<Node.Expression>
                if (type.pieces.size == 1 && type.pieces[0].name.name == "List") {
                    val typeArgs = type.pieces[0].typeArgs
                    if (typeArgs != null && typeArgs.elements.size == 1) {
                        val typeArg = typeArgs.elements[0]
                        return simpleType(
                            pieces = type.pieces.map {
                                it.copy(
                                    typeArgs = typeArgs(
                                        typeArg.copy(
                                            typeRef = typeArg.typeRef?.copy(
                                                type = toFqNameType(
                                                    typeArg.type as Node.Type.Simple,
                                                    nestedNames
                                                ),
                                            ),
                                        )
                                    )
                                )
                            },
                        )
                    }
                }

                val mutableNestedNames = nestedNames.toMutableList()
                val pieceNames = type.pieces.map { it.name.name }

                while (true) {
                    val names = mutableNestedNames + pieceNames
                    if (fqNames.contains(names)) {
                        return simpleType(
                            pieces = names.map { piece(name = nameExpression(it)) }
                        )
                    }
                    if (mutableNestedNames.isEmpty()) {
                        break
                    }
                    mutableNestedNames.removeLast()
                }

                if (pieceNames == listOf("Receiver")) {
                    return simpleType(
                        pieces = listOf(
                            piece(name = nameExpression("Node")),
                            piece(name = nameExpression("Expression")),
                            piece(name = nameExpression("DoubleColon")),
                        ) + type.pieces
                    )
                }

                return type
            }

            object : Visitor() {
                override fun visit(v: Node, parent: Node?) {
                    if (v is Node.Declaration.Class) {
                        val name = v.name?.name.orEmpty()
                        nestedNames.add(name)

                        if (v.isDataClass) {
                            val params = v.primaryConstructor?.params?.elements.orEmpty()
                            val functionName = toFunctionName(nestedNames)

                            val func = functionDeclaration(
                                name = nameExpression(functionName),
                                params = functionParams(
                                    elements = params.map { p ->
                                        val fqType = when (val type = p.typeRef?.type) {
                                            is Node.Type.Simple -> toFqNameType(type, nestedNames)
                                            is Node.Type.Nullable -> type.copy(
                                                type = toFqNameType(type.type as Node.Type.Simple, nestedNames)
                                            )
                                            else -> type
                                        }
                                        functionParam(
                                            name = p.name,
                                            typeRef = p.typeRef?.copy(type = fqType),
                                            defaultValue = defaultValueOf(fqType),
                                        )
                                    },
                                ),
                                body = callExpression(
                                    expression = nameExpression(nestedNames.joinToString(".")),
                                    args = valueArgs(
                                        elements = params.map { p ->
                                            valueArg(
                                                name = p.name,
                                                expression = expressionOf(functionName, p.name),
                                            )
                                        },
                                    ),
                                )
                            )
//                            println(nestedNames.joinToString(".") + "\t" + toFunctionName(nestedNames))
                            stringBuilder.appendLine(Writer.write(func))
                            val firstParam = func.params?.elements?.firstOrNull()
                            if (firstParam != null && listOf(
                                    "elements",
                                    "statements",
                                    "decls",
                                    "pieces",
                                ).contains(firstParam.name.name)
                            ) {
                                val firstParamType = firstParam.typeRef?.type as? Node.Type.Simple
                                if (firstParamType != null) {
                                    if (firstParamType.pieces.firstOrNull()?.name?.name == "List") {
                                        val listElementType = firstParamType.pieces.first().typeArgs!!.elements[0].type
                                        if (listElementType != null) {
                                            val varargFunc = functionDeclaration(
                                                name = nameExpression(functionName),
                                                params = functionParams(
                                                    functionParam(
                                                        modifiers = modifiers(keywordModifier(Node.Modifier.Keyword.Token.VARARG)),
                                                        name = nameExpression(firstParam.name.name),
                                                        typeRef = typeRef(type = listElementType),
                                                    )
                                                ),
                                                body = callExpression(
                                                    expression = nameExpression(functionName),
                                                    args = valueArgs(
                                                        valueArg(
                                                            expression = nameExpression("${firstParam.name.name}.toList()"),
                                                        )
                                                    )
                                                )
                                            )
                                            stringBuilder.appendLine(Writer.write(varargFunc))
                                        }
                                    }
                                }
                            }
                        }

                        super.visit(v, parent)

                        nestedNames.removeLast()
                    } else {
                        super.visit(v, parent)
                    }
                }
            }.visit(ctx.fileNode)
        }
        .toSource()
        .also {
            java.io.File("ktcodeshift-dsl/src/main/kotlin/ktcodeshift/Builder.kt")
                .writeText(stringBuilder.toString(), StandardCharsets.UTF_8)
        }
}

fun toFunctionName(nestedNames: List<String>): String {
    val name = nestedNames.last()
    val fqName = nestedNames.joinToString(".")
    fun isChildOf(parent: String): Boolean {
        val prefix = "$parent."
        return fqName.startsWith(prefix) && nestedNames.size == prefix.count { it == '.' } + 1
    }
    if (isChildOf("Node.Declaration")) {
        return "${name.decapitalizeSmart()}Declaration"
    }
    if (isChildOf("Node.Type")) {
        return "${name.decapitalizeSmart()}Type"
    }
    if (isChildOf("Node.Expression")) {
        return "${name.decapitalizeSmart()}Expression"
    }
    if (isChildOf("Node.Expression.When.Branch")) {
        return "${name.decapitalizeSmart()}Branch"
    }
    if (isChildOf("Node.Expression.When.Condition")) {
        return "${name.decapitalizeSmart()}Condition"
    }
    if (isChildOf("Node.Expression.DoubleColon.Receiver")) {
        return "${name.decapitalizeSmart()}DoubleColonReceiver"
    }
    if (isChildOf("Node.Declaration.Class.Parent")) {
        return "${name.decapitalizeSmart()}Parent"
    }
    return when (fqName) {
        "Node.Declaration.Class.Body" -> "classBody"
        "Node.Declaration.Function.Params" -> "functionParams"
        "Node.Declaration.Function.Param" -> "functionParam"
        "Node.Type.Function.Receiver" -> "functionTypeReceiver"
        "Node.Type.Function.Params" -> "functionTypeParams"
        "Node.Type.Function.Param" -> "functionTypeParam"
        "Node.Expression.Lambda.Params" -> "lambdaParams"
        "Node.Expression.Lambda.Param" -> "lambdaParam"
        "Node.Expression.Binary.Operator" -> "binaryOperator"
        "Node.Expression.Unary.Operator" -> "unaryOperator"
        "Node.Expression.BinaryType.Operator" -> "binaryTypeOperator"
        "Node.Modifier.AnnotationSet.Target" -> "annotationSetTarget"
        "Node.Modifier.Keyword" -> "keywordModifier"
        else -> name.decapitalizeSmart()
    }
}

fun defaultValueOf(type: Node.Type?): Node.Expression? {
    return if (type is Node.Type.Nullable) {
        nameExpression("null")
    } else if (type is Node.Type.Simple) {
        val fqName = type.pieces.joinToString(".") { it.name.name }
        if (fqName == "List") {
            nameExpression("listOf()")
        } else if (fqName == "Boolean") {
            nameExpression("false")
        } else {
            if (fqName.startsWith("Node.Keyword.") && !(fqName.contains(".ValOrVar") || fqName.contains(".Declaration"))) {
                nameExpression("$fqName()")
            } else {
                null
            }
        }
    } else {
        null
    }
}

fun expressionOf(functionName: String, paramName: Node.Expression.Name): Node.Expression {
    if (paramName.name == "equals") {
        val expressionText = when (functionName) {
            "functionDeclaration", "getter", "setter" -> "if (equals == null && body != null && body !is Node.Expression.Block) Node.Keyword.Equal() else equals"
            "functionParam" -> "if (equals == null && defaultValue != null) Node.Keyword.Equal() else equals"
            "propertyDeclaration" -> "if (equals == null && initializer != null) Node.Keyword.Equal() else equals"
            else -> null
        }
        if (expressionText != null) {
            return nameExpression(expressionText)
        }
    }
    return paramName
}
