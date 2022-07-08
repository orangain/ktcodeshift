package ktcodeshift

import ktast.ast.Node

val Node.WithModifiers.annotations: List<Node.Modifier.AnnotationSet.Annotation>
    get() = annotationSets.flatMap { it.annotations }

val Node.WithModifiers.keywordModifiers: List<Node.Modifier.Keyword>
    get() = modifiers?.elements.orEmpty().mapNotNull { it as? Node.Modifier.Keyword }

val Node.Declaration.Class.isDataClass: Boolean
    get() = keywordModifiers.contains(Node.Modifier.Keyword(Node.Modifier.Keyword.Token.DATA))


fun Node.Type.asFunctionType(): Node.Type.Function = this as Node.Type.Function
fun Node.Type.asFunctionTypeOrNull(): Node.Type.Function? = this as? Node.Type.Function
fun Node.Type.asSimpleType(): Node.Type.Simple = this as Node.Type.Simple
fun Node.Type.asSimpleTypeOrNull(): Node.Type.Simple? = this as? Node.Type.Simple
fun Node.Type.asNullableType(): Node.Type.Nullable = this as Node.Type.Nullable
fun Node.Type.asNullableTypeOrNull(): Node.Type.Nullable? = this as? Node.Type.Nullable
fun Node.Type.asDynamicType(): Node.Type.Dynamic = this as Node.Type.Dynamic
fun Node.Type.asDynamicTypeOrNull(): Node.Type.Dynamic? = this as? Node.Type.Dynamic

val Node.TypeArg.type: Node.Type?
    get() = (this as? Node.TypeArg)?.typeRef?.type
