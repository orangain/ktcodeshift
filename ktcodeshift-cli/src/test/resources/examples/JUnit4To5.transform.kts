import ktast.ast.Node
import ktcodeshift.*

val annotationNameMap = mapOf(
    "Before" to "BeforeEach",
    "After" to "AfterEach",
    "BeforeClass" to "BeforeAll",
    "AfterClass" to "AfterAll",
    "Ignore" to "Disabled",
)

transform { fileInfo ->
    Api
        .parse(fileInfo.source)
        .find<Node.Import>()
        .filter { v ->
            v.names.size == 3 && v.names.take(2).map { it.name } == listOf("org", "junit")
        }
        .replaceWith { v ->
            v.copy(
                names = v.names.take(2) + listOf(
                    Node.Expr.Name("jupiter"),
                    Node.Expr.Name("api"),
                    Node.Expr.Name(v.names[2].name.let { annotationNameMap[it] ?: it }),
                )
            )
        }
        .find<Node.Modifier.AnnotationSet.Annotation>()
        .replaceWith { v ->
            val name = annotationNameMap[v.constructorCallee.type.pieces.last().name.name]?.let(Node.Expr::Name)
            if (name != null) {
                v.copy(
                    constructorCallee = Node.ConstructorCallee(
                        type = Node.Type.Simple(
                            pieces = v.constructorCallee.type.pieces.dropLast(1) + v.constructorCallee.type.pieces.last()
                                .copy(name = name)
                        )
                    )
                )
            } else {
                v
            }
        }
        .find<Node.Decl.Func>()
        .filter { v ->
            val annotation = getAnnotationByName(v.mods, "Test")
            getValueArgByName(annotation?.args, "expected") != null
        }
        .replaceWith { v ->
            val annotation = getAnnotationByName(v.mods, "Test")
            val arg = getValueArgByName(annotation?.args, "expected")
            val exceptionType = ((arg?.expr as Node.Expr.DoubleColonRef.Class).recv as Node.Expr.DoubleColonRef.Recv.Type).type
            val originalStatements = (v.body as Node.Decl.Func.Body.Block).block.statements

            v.copy(
                mods = v.mods!!.copy(
                    elements = v.mods!!.elements.map {
                        if (it is Node.Modifier.AnnotationSet && it.anns.contains(annotation)) {
                            it.copy(anns = listOf(annotation!!.copy(args = null)))
                        } else {
                            it
                        }
                    }
                ),
                body = Node.Decl.Func.Body.Block(
                    block = Node.Expr.Block(
                        statements = listOf(
                            Node.Expr.Call(
                                expr = Node.Expr.Name("Assertions.assertThrows"),
                                typeArgs = Node.TypeArgs(
                                    elements = listOf(Node.TypeArg.Type(
                                        mods = null,
                                        typeRef = Node.TypeRef(
                                            lPar = null,
                                            mods = null,
                                            innerLPar = null,
                                            innerMods = null,
                                            type = exceptionType,
                                            innerRPar = null,
                                            rPar = null,
                                        )
                                    )),
                                    trailingComma = null,
                                ),
                                args = null,
                                lambdaArgs = listOf(
                                    Node.Expr.Call.LambdaArg(
                                    anns = listOf(),
                                    label = null,
                                    func = Node.Expr.Lambda(
                                        params = null,
                                        body = Node.Expr.Lambda.Body(originalStatements),
                                    ),
                                )),
                            )
                        )
                    )
                )
            )
        }
        .toSource()
}

fun getAnnotationByName(modifiers: Node.Modifiers?, name: String): Node.Modifier.AnnotationSet.Annotation? {
    if (modifiers == null) {
        return null
    }

    modifiers.elements.forEach { e ->
        if (e is Node.Modifier.AnnotationSet) {
            val annotation = e.anns.find { it.constructorCallee.type.pieces.last().name.name == name }
            if (annotation != null) {
                return annotation
            }
        }
    }
    return null
}

fun getValueArgByName(args: Node.ValueArgs?, name: String): Node.ValueArg? {
    if (args == null) {
        return null
    }

    return args.elements.find { it.name?.name == name }
}
