package ktcodeshift

import ktast.ast.Node

val Node.WithModifiers.annotations: List<Node.Modifier.AnnotationSet.Annotation>
    get() = annotationSets.flatMap { it.annotations }

val Node.WithModifiers.keywordModifiers: List<Node.Modifier.Keyword>
    get() = modifiers?.elements.orEmpty().mapNotNull { it as? Node.Modifier.Keyword }

val Node.Declaration.Class.isDataClass: Boolean
    get() = isClass && keywordModifiers.contains(Node.Modifier.Keyword(Node.Modifier.Keyword.Token.DATA))
