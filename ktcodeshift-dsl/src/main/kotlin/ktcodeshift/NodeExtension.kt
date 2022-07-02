package ktcodeshift

import ktast.ast.Node

val Node.WithModifiers.annotationSets: List<Node.Modifier.AnnotationSet>
    get() = mods?.elements.orEmpty().mapNotNull { it as? Node.Modifier.AnnotationSet }

val Node.WithModifiers.annotations: List<Node.Modifier.AnnotationSet.Annotation>
    get() = annotationSets.flatMap { it.anns }

val Node.WithModifiers.literalModifiers: List<Node.Modifier.Lit>
    get() = mods?.elements.orEmpty().mapNotNull { it as? Node.Modifier.Lit }

val Node.Decl.Structured.isDataClass: Boolean
    get() = literalModifiers.contains(Node.Modifier.Lit(Node.Modifier.Keyword.DATA))


fun Node.Type.asFunctionType(): Node.Type.Func = this as Node.Type.Func
fun Node.Type.asFunctionTypeOrNull(): Node.Type.Func? = this as? Node.Type.Func
fun Node.Type.asSimpleType(): Node.Type.Simple = this as Node.Type.Simple
fun Node.Type.asSimpleTypeOrNull(): Node.Type.Simple? = this as? Node.Type.Simple
fun Node.Type.asNullableType(): Node.Type.Nullable = this as Node.Type.Nullable
fun Node.Type.asNullableTypeOrNull(): Node.Type.Nullable? = this as? Node.Type.Nullable
fun Node.Type.asDynamicType(): Node.Type.Dynamic = this as Node.Type.Dynamic
fun Node.Type.asDynamicTypeOrNull(): Node.Type.Dynamic? = this as? Node.Type.Dynamic

val Node.TypeArg.type: Node.Type?
    get() = (this as? Node.TypeArg.Type)?.typeRef?.type
