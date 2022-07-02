import ktast.ast.Node
import ktast.ast.Visitor
import ktast.ast.Writer
import ktcodeshift.*
import org.jetbrains.kotlin.util.capitalizeDecapitalize.decapitalizeSmart

transform { fileInfo ->
    Api
        .parse(fileInfo.source)
        .also { ctx ->
            val nestedNames = mutableListOf<String>()
            val fqNames = mutableSetOf<List<String>>()

            object : Visitor() {
                override fun visit(v: Node?, parent: Node) {
                    if (v is Node.Decl.Structured) {
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
            println("import ktast.ast.Node")

            fun toFqNameType(type: Node.Type.Simple, nestedNames: List<String>): Node.Type.Simple {

                if (type.pieces.size == 1 && type.pieces[0].name.name == "List") {
                    val typeArgs = type.pieces[0].typeArgs
                    if (typeArgs != null && typeArgs.elements.size == 1) {
                        val typeArg = typeArgs.elements[0] as Node.TypeArg.Type
                        return simpleType(
                            pieces = type.pieces.map {
                                it.copy(typeArgs = typeArgs(
                                    elements = listOf(typeArg.copy(
                                        typeRef = typeArg.typeRef.copy(
                                            type = toFqNameType(typeArg.typeRef.type as Node.Type.Simple, nestedNames),
                                        ),
                                    )),
                                    trailingComma = null,
                                ))
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
                            pieces = names.map { piece(name = nameExpression(it), typeArgs = null) }
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
                override fun visit(v: Node?, parent: Node) {
                    if (v is Node.Decl.Structured) {
                        val name = v.name?.name.orEmpty()
                        nestedNames.add(name)

                        if (v.mods?.elements.orEmpty().any { it is Node.Modifier.Lit && it.keyword == Node.Modifier.Keyword.DATA }) {
                            val params = v.primaryConstructor?.params?.elements.orEmpty()

                            val func = func(
                                name = nameExpression(toFunctionName(nestedNames)),
                                params = functionParams(
                                    elements = params.map { p ->
                                        val fqType = when (val type = p.typeRef?.type) {
                                            is Node.Type.Simple -> toFqNameType(type, nestedNames)
                                            is Node.Type.Nullable -> type.copy(type = toFqNameType(type.type as Node.Type.Simple, nestedNames))
                                            else -> type
                                        }
                                        param(
                                            name = p.name,
                                            typeRef = p.typeRef?.copy(type=fqType),
                                            initializer = initializerOf(fqType),
                                        )
                                    },
                                ),
                                body = expr(
                                    equals = Node.Keyword.Equal(),
                                    expr = callExpression(
                                        expr = nameExpression(nestedNames.joinToString(".")),
                                        args = valueArgs(
                                            elements = params.map { p ->
                                                valueArg(
                                                    name = p.name,
                                                    asterisk = false,
                                                    expr = p.name,
                                                )
                                            },
                                        ),
                                    )
                                )
                            )
//                            println(nestedNames.joinToString(".") + "\t" + toFunctionName(nestedNames))
                            println(Writer.write(func))
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
}

fun toFunctionName(nestedNames: List<String>): String {
    val name = nestedNames.last()
    val fqName = nestedNames.joinToString(".")
    fun isChildOf(parent: String): Boolean {
        val prefix = "$parent."
        return fqName.startsWith(prefix) && nestedNames.size == prefix.count { it == '.' } + 1
    }
    if (isChildOf("Node.Decl.Property.Variable")) {
        return "variable"
    }
    if (isChildOf("Node.Type")) {
        return "${name.decapitalizeSmart()}Type"
    }
    if (isChildOf("Node.Expr")) {
        return "${name.decapitalizeSmart()}Expression"
    }
    if (isChildOf("Node.Expr.When.Entry")) {
        return "whenEntry$name"
    }
    if (isChildOf("Node.Expr.When.Cond")) {
        return "whenCondition$name"
    }
    return when (fqName) {
        "Node.Package" -> "packageDirective"
        "Node.Imports" -> "importDirectives"
        "Node.Import" -> "importDirective"
        "Node.Decl.Func.Params" -> "functionParams"
        "Node.Expr.DoubleColonRef.Class" -> "doubleColonClassLiteral"
        else -> name.decapitalizeSmart()
    }
}

fun initializerOf(type: Node.Type?): Node.Initializer? {
    val expr = if (type is Node.Type.Nullable) {
        Node.Expr.Name("null")
    } else if (type is Node.Type.Simple) {
        if (type.pieces.size == 1 && type.pieces[0].name.name == "List") {
            Node.Expr.Name("listOf()")
        } else {
            val fqName = type.pieces.joinToString(".") { it.name.name }
            if (fqName.startsWith("Node.Keyword.") && !(fqName.contains(".ValOrVar") || fqName.contains(".Declaration"))) {
                Node.Expr.Name("$fqName()")
            } else {
                null
            }
        }
    } else {
        null
    }

    return expr?.let { Node.Initializer(Node.Keyword.Equal(), it) }
}
