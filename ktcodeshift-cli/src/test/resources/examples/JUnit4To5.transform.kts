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
                    pieces = n.type.pieces.map { p ->
                        simpleTypePiece(nameExpression(annotationNameMap[p.name.text] ?: p.name.text))
                    }
                )
            )
        }
        .find<Node.Declaration.FunctionDeclaration>()
        .filter { n ->
            val annotation = getAnnotationByName(n.annotations, "Test")
            getValueArgByName(annotation?.args, "expected") != null
        }
        .replaceWith { n ->
            val annotation = getAnnotationByName(n.annotations, "Test")!!
            val arg = getValueArgByName(annotation.args, "expected")!!
            val exceptionType = expressionToSimpleType((arg.expression as Node.Expression.ClassLiteralExpression).lhs!!)
            val originalStatements = (n.body as Node.Expression.BlockExpression).statements

            n.copy(
                modifiers = n.modifiers.map { modifier ->
                    if (modifier is Node.Modifier.AnnotationSet && modifier.annotations.contains(annotation)) {
                        modifier.copy(
                            annotations = listOf(
                                annotation.copy(
                                    lPar = null,
                                    args = listOf(),
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
                        typeArgs = listOf(
                            typeArg(
                                type = exceptionType,
                            )
                        ),
                        lambdaArg = lambdaArg(
                            expression = lambdaExpression(
                                statements = originalStatements,
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
    return annotations.find { annotation ->
        annotation.type.pieces.joinToString(".") { it.name.text } == name
    }
}

fun getValueArgByName(args: List<Node.ValueArg>?, name: String): Node.ValueArg? {
    return args.orEmpty().find { it.name?.text == name }
}

fun expressionToSimpleType(e: Node.Expression): Node.Type.SimpleType {
    val names = generateSequence(e) { (it as? Node.Expression.BinaryExpression)?.lhs }
        .map { (if (it is Node.Expression.BinaryExpression) it.rhs else it) as Node.Expression.NameExpression }
        .toList()
        .reversed()

    return simpleType(
        pieces = names.map { simpleTypePiece(it) }
    )
}
