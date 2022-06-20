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
        .preVisit { v, _ ->
            if (v is Node.Import) {
                if (v.names.size == 3 && v.names.take(2).map { it.name } == listOf("org", "junit")) {
                    v.copy(names = v.names.take(2) + listOf(
                            Node.Expr.Name("jupiter"),
                            Node.Expr.Name("api"),
                            Node.Expr.Name(v.names[2].name.let { annotationNameMap[it] ?: it }),
                        )
                    )
                } else {
                    v
                }
            } else {
                v
            }
        }
        .preVisit { v, _ ->
            if (v is Node.Modifier.AnnotationSet.Annotation) {
                val name = annotationNameMap[v.constructorCallee.type.pieces.last().name.name]?.let(Node.Expr::Name)

                if (name != null) {
                    v.copy(
                        constructorCallee = Node.ConstructorCallee(
                            type = Node.Type.Simple(
                                pieces = v.constructorCallee.type.pieces.dropLast(1) + v.constructorCallee.type.pieces.last().copy(name=name)
                            )
                        )
                    )
                } else {
                    v
                }
            } else {
                v
            }
        }
        .toSource()
}
