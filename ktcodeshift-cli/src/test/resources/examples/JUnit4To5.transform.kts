import ktast.ast.Node
import ktcodeshift.*

transform { fileInfo ->
    Api
        .parse(fileInfo.source)
        .preVisit { v, _ ->
            if (v is Node.Modifier.AnnotationSet.Annotation) {
                val name = when (v.constructorCallee.type.pieces.last().name.name) {
                    "Before" -> "BeforeEach"
                    "After" -> "AfterEach"
                    "BeforeClass" -> "BeforeAll"
                    "AfterClass" -> "AfterAll"
                    else -> null
                }?.let(Node.Expr::Name)

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
