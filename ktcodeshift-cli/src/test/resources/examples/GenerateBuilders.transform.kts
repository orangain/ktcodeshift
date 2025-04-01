/*
You can generate ktcodeshift-dsl/src/main/kotlin/ktcodeshift/Builder.kt using the following command:

ktcodeshift -t ktcodeshift-cli/src/test/resources/examples/GenerateBuilders.transform.kts ../ktast/ast/src/commonMain/kotlin/ktast/ast/Node.kt

*/

import org.jetbrains.kotlin.util.capitalizeDecapitalize.decapitalizeSmart
import java.nio.charset.StandardCharsets

transform { fileInfo ->
    val stringBuilder = StringBuilder()
    Ktcodeshift
        .parse(fileInfo.source)
        .also { fileNode ->
            val fqNames = mutableSetOf<List<String>>()

            object : Visitor() {
                override fun visit(path: NodePath<*>) {
                    val node = path.node
                    if (node is Node.Declaration.ClassDeclaration) {
                        fqNames.add(nestedClassNames(path))
                    }
                    super.visit(path)
                }
            }.traverse(fileNode)

            println(fqNames)
            println("-".repeat(40))
            stringBuilder.appendLine("package ktcodeshift")
            stringBuilder.appendLine()
            stringBuilder.appendLine("import ktast.ast.Node")
            stringBuilder.appendLine("import ktast.ast.NodeSupplement")

            GeneratorVisitor(stringBuilder, fqNames).traverse(fileNode)
        }
    java.io.File("ktcodeshift-dsl/src/main/kotlin/ktcodeshift/Builder.kt")
        .writeText(stringBuilder.toString(), StandardCharsets.UTF_8)

    null
}

fun nestedClassNames(path: NodePath<*>): List<String> {
    val nestedClasses = (path.ancestors().toList().reversed() + path.node)
        .filterIsInstance<Node.Declaration.ClassDeclaration>()
    return nestedClasses.map { it.name.text }
}

fun functionNameOf(className: String): String {
    return className.decapitalizeSmart()
}

fun defaultValueOf(parameterName: String, type: Node.Type?): Node.Expression? {
    return if (type is Node.Type.NullableType) {
        nameExpression("null")
    } else if (type is Node.Type.SimpleType) {
        val fqName = (type.qualifiers.map { it.name } + type.name).joinToString(".") { it.text }
        if (fqName == "List") {
            if (parameterName == "variables") {
                null
            } else {
                nameExpression("listOf()")
            }
        } else if (fqName == "Boolean") {
            nameExpression("false")
        } else if (fqName == "NodeSupplement") {
            nameExpression("NodeSupplement()")
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

val parenthesizedParamNames = mapOf(
    "EnumEntry" to "arguments",
    "PropertyDeclaration" to "variables",
    "LambdaParameter" to "variables",
    "Annotation" to "arguments",
)

val angledParamNames = mapOf(
    "ClassDeclaration" to "typeParameters",
    "FunctionDeclaration" to "typeParameters",
    "PropertyDeclaration" to "typeParameters",
    "TypeAliasDeclaration" to "typeParameters",
    "SimpleTypePiece" to "typeArguments",
    "CallExpression" to "typeArguments",
)

val keywordTypes = mapOf(
    "lPar" to "Node.Keyword.LPar",
    "rPar" to "Node.Keyword.RPar",
    "lAngle" to "Node.Keyword.Less",
    "rAngle" to "Node.Keyword.Greater",
    "lBracket" to "Node.Keyword.LBracket",
    "rBracket" to "Node.Keyword.RBracket",
)

fun expressionOf(className: String, paramName: Node.Expression.NameExpression): Node.Expression {
    val keywordType = keywordTypes[paramName.text]
    when (val name = paramName.text) {
        "lPar", "rPar" -> {
            if (className == "Getter") {
                return nameExpression("if (body != null) $name ?: $keywordType() else $name")
            }
            if (className == "Setter") {
                return nameExpression("if (parameter != null) $name ?: $keywordType() else $name")
            }
            if (className == "CallExpression") {
                return nameExpression("if (arguments.isNotEmpty() || lambdaArgument == null) $name ?: $keywordType() else $name")
            }
            when (val parenthesizedParamName = parenthesizedParamNames[className]) {
                null -> {
                    // do nothing
                }
                "variables" -> {
                    return nameExpression("$name ?: $keywordType()")
                }
                else -> {
                    return nameExpression("if ($parenthesizedParamName.isNotEmpty()) $name ?: $keywordType() else $name")
                }
            }
        }
        "lAngle", "rAngle" -> {
            val angledParamName = angledParamNames[className]
            if (angledParamName != null) {
                return nameExpression("if ($angledParamName.isNotEmpty()) $name ?: $keywordType() else $name")
            }
        }
        "lBracket", "rBracket" -> {
            if (className == "AnnotationSet") {
                return nameExpression("if (annotations.size >= 2) $name ?: $keywordType() else $name")
            }
        }
    }
    return paramName.copy()
}

class GeneratorVisitor(
    private val stringBuilder: StringBuilder,
    private val fqNames: Set<List<String>>,
) : Visitor() {
    override fun visit(path: NodePath<*>) {
        val v = path.node
        if (v is Node.Declaration.ClassDeclaration) {
            val nestedNames = nestedClassNames(path)

            if (v.isDataClass && nestedNames[1] != "Keyword") {
                val name = v.name.text
                val parameters = v.primaryConstructor?.parameters.orEmpty()
                val functionName = functionNameOf(name)

                val func = makeBuilderFunction(nestedNames, functionName, parameters, name)
                stringBuilder.appendLine(Writer.write(func))
                val firstParameter = func.parameters.firstOrNull()
                if (firstParameter?.name?.text == "statements") {
                    val firstParamType = firstParameter.type as? Node.Type.SimpleType
                    if (firstParamType != null) {
                        if (firstParamType.name.text == "List") {
                            val listElementType = firstParamType.typeArguments[0].type
                            val varargFunc = makeVarargBuilderFunction(func, firstParameter.name.text, listElementType)
                            stringBuilder.appendLine(Writer.write(varargFunc))
                        }
                    }
                }
                if (func.parameters.map { it.name.text }.containsAll(listOf("lPar", "variables", "rPar"))) {
                    val singleVariableFunc = makeSingleVariableBuilderFunction(func)
                    stringBuilder.appendLine(Writer.write(singleVariableFunc))
                }
            }
        }
        super.visit(path)
    }

    private fun makeBuilderFunction(
        nestedNames: List<String>,
        functionName: String,
        parameters: List<Node.FunctionParameter>,
        name: String
    ) = functionDeclaration(
        supplement = NodeSupplement(
            extrasBefore = listOf(
                comment(
                    """
                        /**
                         * Creates a new [${nestedNames.joinToString(".")}] instance.
                         */
                    """.trimIndent()
                )
            )
        ),
        name = nameExpression(functionName),
        parameters = parameters.map { p ->
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
            functionParameter(
                name = p.name,
                type = fqType,
                defaultValue = defaultValueOf(p.name.text, fqType),
            )
        },
        body = callExpression(
            calleeExpression = nameExpression(nestedNames.joinToString(".")),
            arguments = parameters.map { p ->
                valueArgument(
                    name = p.name,
                    expression = expressionOf(name, p.name),
                )
            },
        )
    )

    private fun makeVarargBuilderFunction(
        func: Node.Declaration.FunctionDeclaration,
        firstParameterName: String,
        listElementType: Node.Type
    ) = func.copy(
        parameters = listOf(
            functionParameter(
                modifiers = listOf(Node.Keyword.Vararg()),
                name = nameExpression(firstParameterName),
                type = listElementType,
            )
        ),
        body = callExpression(
            calleeExpression = func.name!!.copy(),
            arguments = listOf(
                valueArgument(
                    expression = nameExpression("$firstParameterName.toList()"),
                )
            ),
        )
    )

    private fun makeSingleVariableBuilderFunction(func: Node.Declaration.FunctionDeclaration): Node.Declaration.FunctionDeclaration {
        return func.copy(
            parameters = func.parameters.map { param ->
                if (param.name.text == "variables") {
                    param.copy(
                        name = nameExpression("variable"),
                        type = (param.type as Node.Type.SimpleType).typeArguments[0].type,
                        defaultValue = null,
                    )
                } else {
                    param
                }
            }.filterNot {
                setOf("lPar", "rPar", "destructuringType").contains(it.name.text)
            },
            body = (func.body as Node.Expression.CallExpression).copy(
                arguments = (func.body as Node.Expression.CallExpression).arguments.map {
                    when (it.name?.text) {
                        "variables" -> {
                            it.copy(
                                expression = nameExpression("listOf(variable)"),
                            )
                        }
                        "lPar", "rPar", "destructuringType" -> {
                            it.copy(
                                expression = nullLiteralExpression(),
                            )
                        }
                        else -> {
                            it
                        }
                    }
                }
            )
        )
    }

    private fun toFqNameType(type: Node.Type.SimpleType, nestedNames: List<String>): Node.Type.SimpleType {
        // e.g. Make List<Expression> to List<Node.Expression>
        if (type.name.text == "List") {
            return type.copy(
                typeArguments = type.typeArguments.map { typeArgument ->
                    typeArgument.copy(
                        type = toFqNameType(
                            typeArgument.type as Node.Type.SimpleType,
                            nestedNames
                        ),
                    )
                }
            )
        }

        generateSequence(nestedNames) { if (it.isNotEmpty()) it.dropLast(1) else null }.forEach { prefixNames ->
            val fqName = prefixNames + type.qualifiers.map { it.name.text } + type.name.text
            if (fqNames.contains(fqName)) {
                return simpleType(
                    qualifiers = fqName.dropLast(1).map { simpleTypeQualifier(nameExpression(it)) },
                    name = nameExpression(fqName.last()),
                )
            }
        }

        return type
    }
}
