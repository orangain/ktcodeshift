package ktcodeshift

import ktast.ast.Node

val Node.WithModifiers.annotationSets: List<Node.Modifier.AnnotationSet>
    get() = mods?.elements.orEmpty().mapNotNull { it as? Node.Modifier.AnnotationSet }

val Node.WithModifiers.annotations: List<Node.Modifier.AnnotationSet.Annotation>
    get() = annotationSets.flatMap { it.anns }

val Node.WithModifiers.literalModifiers: List<Node.Modifier.Lit>
    get() = mods?.elements.orEmpty().mapNotNull { it as? Node.Modifier.Lit }
