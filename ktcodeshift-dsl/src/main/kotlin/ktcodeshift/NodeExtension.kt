package ktcodeshift

import ktast.ast.Node

val Node.WithModifiers.annotations: List<Node.Modifier.AnnotationSet.Annotation>
    get() = annotationSets.flatMap { it.annotations }

val Node.WithModifiers.keywordModifiers: List<Node.Modifier.KeywordModifier>
    get() = modifiers.mapNotNull { it as? Node.Modifier.KeywordModifier }

val Node.Declaration.ClassDeclaration.isDataClass: Boolean
    get() = isClass && keywordModifiers.any { it is Node.Keyword.Data }
