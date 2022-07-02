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
                    nameExpression("jupiter"),
                    nameExpression("api"),
                    nameExpression(v.names[2].name.let { annotationNameMap[it] ?: it }),
                )
            )
        }
        .find<Node.Modifier.AnnotationSet.Annotation>()
        .replaceWith { v ->
            val name = annotationNameMap[v.constructorCallee.type.pieces.last().name.name]?.let(::nameExpression)
            if (name != null) {
                v.copy(
                    constructorCallee = constructorCallee(
                        type = simpleType(
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
            val annotation = getAnnotationByName(v.annotations, "Test")
            getValueArgByName(annotation?.args, "expected") != null
        }
        .replaceWith { v ->
            val annotation = getAnnotationByName(v.annotations, "Test")
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
                body = block(
                    block = blockExpression(
                        statements = listOf(
                            callExpression(
                                expr = nameExpression("Assertions.assertThrows"),
                                typeArgs = typeArgs(
                                    elements = listOf(type(
                                        typeRef = typeRef(
                                            type = exceptionType,
                                        )
                                    )),
                                ),
                                lambdaArgs = listOf(
                                    lambdaArg(
                                    func = lambdaExpression(
                                        body = body(originalStatements),
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

fun getAnnotationByName(annotations: List<Node.Modifier.AnnotationSet.Annotation>, name: String): Node.Modifier.AnnotationSet.Annotation? {
    return annotations.find { it.constructorCallee.type.pieces.last().name.name == name }
}

fun getValueArgByName(args: Node.ValueArgs?, name: String): Node.ValueArg? {
    if (args == null) {
        return null
    }

    return args.elements.find { it.name?.name == name }
}
