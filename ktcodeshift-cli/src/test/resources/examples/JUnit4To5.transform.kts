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
        .filter { n ->
            n.names.size == 3 && n.names.take(2).map { it.text } == listOf("org", "junit")
        }
        .replaceWith { n ->
            n.copy(
                names = n.names.take(2) + listOf(
                    nameExpression("jupiter"),
                    nameExpression("api"),
                    nameExpression(n.names[2].text.let { annotationNameMap[it] ?: it }),
                )
            )
        }
        .find<Node.Modifier.AnnotationSet.Annotation>()
        .replaceWith { n ->
            n.copy(
                type = n.type.copy(
                    name = n.type.name.copy(text = annotationNameMap[n.type.name.text] ?: n.type.name.text)
                )
            )
        }
        .find<Node.Declaration.FunctionDeclaration>()
        .filter { n ->
            val annotation = getAnnotationByName(n.annotations, "Test")
            getValueArgumentByName(annotation?.arguments, "expected") != null
        }
        .replaceWith { n ->
            val annotation = getAnnotationByName(n.annotations, "Test")!!
            val argument = getValueArgumentByName(annotation.arguments, "expected")!!
            val exceptionClassLiteral = argument.expression as Node.Expression.ClassLiteralExpression
            val methodBody = n.body as Node.Expression.BlockExpression

            n.copy(
                modifiers = n.modifiers.map { modifier ->
                    if (modifier is Node.Modifier.AnnotationSet && modifier.annotations.contains(annotation)) {
                        modifier.copy(
                            annotations = listOf(
                                annotation.copy(
                                    lPar = null,
                                    arguments = listOf(),
                                    rPar = null,
                                )
                            )
                        )
                    } else {
                        modifier
                    }
                },
                body = blockExpression(
                    callExpression(
                        calleeExpression = nameExpression("Assertions.assertThrows"),
                        typeArguments = listOf(
                            typeArgument(
                                type = exceptionClassLiteral.lhsAsType()!!,
                            )
                        ),
                        lambdaArgument = lambdaExpression(
                            statements = methodBody.statements,
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
    return annotations.find { it.type.name.text == name }
}

fun getValueArgumentByName(args: List<Node.ValueArgument>?, name: String): Node.ValueArgument? {
    return args.orEmpty().find { it.name?.text == name }
}
