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
        .find<Node.ImportDirective>()
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
            val name = annotationNameMap[v.type.pieces.last().name.name]?.let(::nameExpression)
            if (name != null) {
                v.copy(
                    type = simpleType(
                        pieces = v.type.pieces.dropLast(1) + v.type.pieces.last()
                            .copy(name = name)
                    )
                )
            } else {
                v
            }
        }
        .find<Node.Declaration.Function>()
        .filter { v ->
            val annotation = getAnnotationByName(v.annotations, "Test")
            getValueArgByName(annotation?.args, "expected") != null
        }
        .replaceWith { v ->
            val annotation = getAnnotationByName(v.annotations, "Test")
            val arg = getValueArgByName(annotation?.args, "expected")
            val exceptionType =
                ((arg?.expression as Node.Expression.ClassLiteral).lhs as Node.Expression.DoubleColon.Receiver.Type).type
            val originalStatements = (v.body as Node.Expression.Block).statements

            v.copy(
                modifiers = v.modifiers!!.copy(
                    elements = v.modifiers!!.elements.map {
                        if (it is Node.Modifier.AnnotationSet && it.annotations.contains(annotation)) {
                            it.copy(annotations = listOf(annotation!!.copy(args = null)))
                        } else {
                            it
                        }
                    }
                ),
                body = blockExpression(
                    callExpression(
                        expression = nameExpression("Assertions.assertThrows"),
                        typeArgs = typeArgs(
                            typeArg(
                                typeRef = typeRef(
                                    type = exceptionType,
                                )
                            )
                        ),
                        lambdaArg = lambdaArg(
                            func = lambdaExpression(
                                body = body(originalStatements),
                            ),
                        ),
                    )
                )
            )
        }
        .toSource()
}

fun getAnnotationByName(
    annotations: List<Node.Modifier.AnnotationSet.Annotation>,
    name: String
): Node.Modifier.AnnotationSet.Annotation? {
    return annotations.find { it.type.pieces.last().name.name == name }
}

fun getValueArgByName(args: Node.ValueArgs?, name: String): Node.ValueArg? {
    if (args == null) {
        return null
    }

    return args.elements.find { it.name?.name == name }
}
