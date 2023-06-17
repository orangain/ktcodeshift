/*
You can generate ktcodeshift-dsl/src/main/kotlin/ktcodeshift/Builder.kt using the following command:

ktcodeshift -t ktcodeshift-cli/src/test/resources/examples/GenerateBuilders.transform.kts ../ktast/ast/src/commonMain/kotlin/ktast/ast/Node.kt

*/

import ktast.ast.Node
import ktast.ast.NodePath
import ktast.ast.Visitor
import ktast.ast.Writer
import ktcodeshift.*
import org.jetbrains.kotlin.util.capitalizeDecapitalize.decapitalizeSmart
import java.nio.charset.StandardCharsets

data class FullyQualifiedName(
    val names: List<String>,
)

transform { fileInfo ->
    val stringBuilder = StringBuilder()
    Api
        .parse(fileInfo.source)
        .also { ctx ->
            val fqNames = mutableSetOf<FullyQualifiedName>()

            object : Visitor() {
                override fun visit(path: NodePath<*>) {
                    val node = path.node
                    if (node is Node.Declaration.ClassDeclaration && node.name != null) {
                        val nestedNames = nestedClassNames(path)
                        fqNames.add(FullyQualifiedName(nestedNames))
                    }
                    super.visit(path)
                }
            }.traverse(ctx.fileNode)

            println(fqNames)
            println("-".repeat(40))
            stringBuilder.appendLine("package ktcodeshift")
            stringBuilder.appendLine()
            stringBuilder.appendLine("import ktast.ast.Node")

            fun toFqNameType(type: Node.Type.SimpleType, nestedNames: List<String>): Node.Type.SimpleType {

                // e.g. Make List<Expression> to List<Node.Expression>
                if (type.pieces.last().name.text == "List") {
                    return type.copy(
                        pieces = type.pieces.map { piece ->
                            piece.copy(
                                typeArgs = piece.typeArgs.map { typeArg ->
                                    typeArg.copy(
                                        type = toFqNameType(
                                            typeArg.type as Node.Type.SimpleType,
                                            nestedNames
                                        ),
                                    )
                                }
                            )
                        }
                    )
                }

                val mutableNestedNames = nestedNames.toMutableList()

                while (true) {
                    val fqName = FullyQualifiedName(
                        mutableNestedNames + type.pieces.map { it.name.text },
                    )
                    if (fqNames.contains(fqName)) {
                        return simpleType(
                            pieces = fqName.names.map { simpleTypePiece(nameExpression(it)) },
                        )
                    }
                    if (mutableNestedNames.isEmpty()) {
                        break
                    }
                    mutableNestedNames.removeLast()
                }

                return type
            }

            object : Visitor() {
                override fun visit(path: NodePath<*>) {
                    val v = path.node
                    if (v is Node.Declaration.ClassDeclaration) {
                        val nestedNames = nestedClassNames(path)

                        if (v.isDataClass && nestedNames[1] != "Keyword") {
                            val params = v.primaryConstructor?.params.orEmpty()
                            val functionName = toFunctionName(nestedNames)

                            val func = functionDeclaration(
                                name = nameExpression(functionName),
                                lPar = Node.Keyword.LPar(),
                                params = params.map { p ->
                                    val fqType = when (val type = p.type) {
                                        is Node.Type.SimpleType -> toFqNameType(type, nestedNames)
                                        is Node.Type.NullableType -> type.copy(
                                            innerType = toFqNameType(
                                                type.innerType as Node.Type.SimpleType,
                                                nestedNames
                                            )
                                        )
                                        else -> type
                                    }
                                    functionParam(
                                        name = p.name,
                                        type = fqType,
                                        defaultValue = defaultValueOf(fqType),
                                    )
                                },
                                rPar = Node.Keyword.RPar(),
                                body = callExpression(
                                    calleeExpression = nameExpression(nestedNames.joinToString(".")),
                                    lPar = Node.Keyword.LPar(),
                                    args = params.map { p ->
                                        valueArg(
                                            name = p.name,
                                            expression = expressionOf(functionName, p.name),
                                        )
                                    },
                                    rPar = Node.Keyword.RPar(),
                                )
                            )
                            stringBuilder.appendLine(Writer.write(func))
                            val firstParam = func.params.firstOrNull()
                            if (firstParam?.name?.text == "statements") {
                                val firstParamType = firstParam.type as? Node.Type.SimpleType
                                if (firstParamType != null) {
                                    if (firstParamType.pieces.last().name.text == "List") {
                                        val listElementType = firstParamType.pieces.last().typeArgs[0].type
                                        val varargFunc = functionDeclaration(
                                            name = nameExpression(functionName),
                                            lPar = Node.Keyword.LPar(),
                                            params = listOf(
                                                functionParam(
                                                    modifiers = listOf(Node.Keyword.Vararg()),
                                                    name = firstParam.name.copy(),
                                                    type = listElementType,
                                                )
                                            ),
                                            rPar = Node.Keyword.RPar(),
                                            body = callExpression(
                                                calleeExpression = nameExpression(functionName),
                                                lPar = Node.Keyword.LPar(),
                                                args = listOf(
                                                    valueArg(
                                                        expression = nameExpression("${firstParam.name.text}.toList()"),
                                                    )
                                                ),
                                                rPar = Node.Keyword.RPar(),
                                            )
                                        )
                                        stringBuilder.appendLine(Writer.write(varargFunc))
                                    }
                                }
                            }
                        }
                    }
                    super.visit(path)
                }
            }.traverse(ctx.fileNode)
        }
        .toSource()
        .also {
            java.io.File("ktcodeshift-dsl/src/main/kotlin/ktcodeshift/Builder.kt")
                .writeText(stringBuilder.toString(), StandardCharsets.UTF_8)
        }
}

fun nestedClassNames(path: NodePath<*>): List<String> {
    val nestedClasses = (path.ancestors().toList().reversed() + path.node)
        .filterIsInstance<Node.Declaration.ClassDeclaration>()
    return nestedClasses.mapNotNull { it.name?.text }
}

fun toFunctionName(nestedNames: List<String>): String {
    val name = nestedNames.last()
    return name.decapitalizeSmart()
}

fun defaultValueOf(type: Node.Type?): Node.Expression? {
    return if (type is Node.Type.NullableType) {
        nameExpression("null")
    } else if (type is Node.Type.SimpleType) {
        val fqName = type.pieces.joinToString(".") { it.name.text }
        if (fqName == "List") {
            nameExpression("listOf()")
        } else if (fqName == "Boolean") {
            nameExpression("false")
        } else {
            if (fqName.startsWith("Node.Keyword.") && !fqName.contains(".ValOrVar")) {
                nameExpression("$fqName()")
            } else {
                null
            }
        }
    } else {
        null
    }
}

fun expressionOf(functionName: String, paramName: Node.Expression.NameExpression): Node.Expression {
    if (paramName.text == "equals") {
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
