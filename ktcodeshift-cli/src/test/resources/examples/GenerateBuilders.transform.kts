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

transform { fileInfo ->
    val stringBuilder = StringBuilder()
    Api
        .parse(fileInfo.source)
        .also { ctx ->
            val fqNames = mutableSetOf<List<String>>()

            object : Visitor() {
                override fun visit(path: NodePath<*>) {
                    val node = path.node
                    if (node is Node.Declaration.ClassDeclaration) {
                        fqNames.add(nestedClassNames(path))
                    }
                    super.visit(path)
                }
            }.traverse(ctx.fileNode)

            println(fqNames)
            println("-".repeat(40))
            stringBuilder.appendLine("package ktcodeshift")
            stringBuilder.appendLine()
            stringBuilder.appendLine("import ktast.ast.Node")
            stringBuilder.appendLine("import ktast.ast.NodeSupplement")

            fun toFqNameType(type: Node.Type.SimpleType, nestedNames: List<String>): Node.Type.SimpleType {

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

            object : Visitor() {
                override fun visit(path: NodePath<*>) {
                    val v = path.node
                    if (v is Node.Declaration.ClassDeclaration) {
                        val nestedNames = nestedClassNames(path)

                        if (v.isDataClass && nestedNames[1] != "Keyword") {
                            val name = v.name.text
                            val parameters = v.primaryConstructor?.parameters.orEmpty()
                            val functionName = name.decapitalizeSmart()

                            val func = functionDeclaration(
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
                                        defaultValue = defaultValueOf(fqType),
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
                            stringBuilder.appendLine(Writer.write(func))
                            val firstParameter = func.parameters.firstOrNull()
                            if (firstParameter?.name?.text == "statements") {
                                val firstParamType = firstParameter.type as? Node.Type.SimpleType
                                if (firstParamType != null) {
                                    if (firstParamType.name.text == "List") {
                                        val listElementType = firstParamType.typeArguments[0].type
                                        val varargFunc = functionDeclaration(
                                            name = nameExpression(functionName),
                                            parameters = listOf(
                                                functionParameter(
                                                    modifiers = listOf(Node.Keyword.Vararg()),
                                                    name = firstParameter.name.copy(),
                                                    type = listElementType,
                                                )
                                            ),
                                            body = callExpression(
                                                calleeExpression = nameExpression(functionName),
                                                arguments = listOf(
                                                    valueArgument(
                                                        expression = nameExpression("${firstParameter.name.text}.toList()"),
                                                    )
                                                ),
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
    java.io.File("ktcodeshift-dsl/src/main/kotlin/ktcodeshift/Builder.kt")
        .writeText(stringBuilder.toString(), StandardCharsets.UTF_8)

    null
}

fun nestedClassNames(path: NodePath<*>): List<String> {
    val nestedClasses = (path.ancestors().toList().reversed() + path.node)
        .filterIsInstance<Node.Declaration.ClassDeclaration>()
    return nestedClasses.map { it.name.text }
}

fun defaultValueOf(type: Node.Type?): Node.Expression? {
    return if (type is Node.Type.NullableType) {
        nameExpression("null")
    } else if (type is Node.Type.SimpleType) {
        val fqName = (type.qualifiers.map { it.name } + type.name).joinToString(".") { it.text }
        if (fqName == "List") {
            nameExpression("listOf()")
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
    "PrimaryConstructor" to "parameters",
    "EnumEntry" to "arguments",
    "SecondaryConstructor" to "parameters",
    "FunctionDeclaration" to "parameters",
    "PropertyDeclaration" to "variables",
    "FunctionType" to "parameters",
    "CallExpression" to "arguments",
    "LambdaParameters" to "variables",
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
    when (val name = paramName.text) {
        "lPar", "rPar" -> {
            if (className == "Getter") {
                return nameExpression("if (body != null) $name ?: ${keywordTypes[name]}() else $name")
            }
            if (className == "Setter") {
                return nameExpression("if (parameter != null) $name ?: ${keywordTypes[name]}() else $name")
            }
            val parenthesizedParamName = parenthesizedParamNames[className]
            if (parenthesizedParamName != null) {
                return nameExpression("if ($parenthesizedParamName.isNotEmpty()) $name ?: ${keywordTypes[name]}() else $name")
            }
        }
        "lAngle", "rAngle" -> {
            val angledParamName = angledParamNames[className]
            if (angledParamName != null) {
                return nameExpression("if ($angledParamName.isNotEmpty()) $name ?: ${keywordTypes[name]}() else $name")
            }
        }
        "lBracket", "rBracket" -> {
            if (className == "AnnotationSet") {
                return nameExpression("if (annotations.size >= 2) $name ?: ${keywordTypes[name]}() else $name")
            }
        }
    }
    return paramName.copy()
}
