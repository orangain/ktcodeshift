package ktcodeshift

import ktast.ast.MutableVisitor
import ktast.ast.Node
import ktast.ast.NodePath
import ktast.ast.Writer
import kotlin.reflect.KClass

fun <T : Node> T.traverse(fn: (path: NodePath<*>) -> Node): T {
    return MutableVisitor.traverse(this, fn)
}

fun Node.toSource(): String {
    return Writer.write(this)
}

inline fun <reified T : Node> Node.KotlinEntry.find(): NodeCollection<T> = find(T::class)

fun <T : Node> Node.KotlinEntry.find(kClass: KClass<T>): NodeCollection<T> = find(kClass.java)

fun <T : Node> Node.KotlinEntry.find(javaClass: Class<T>): NodeCollection<T> {
    val nodes = mutableListOf<NodePath<T>>()
    MutableVisitor.traverse(this) { path ->
        if (javaClass.isAssignableFrom(path.node::class.java)) {
            @Suppress("UNCHECKED_CAST")
            nodes.add(path as NodePath<T>)
        }
        path.node
    }
    return NodeCollection(nodes.toList(), this)
}

val Node.WithModifiers.annotations: List<Node.Modifier.AnnotationSet.Annotation>
    get() = annotationSets.flatMap { it.annotations }

val Node.WithModifiers.keywordModifiers: List<Node.Modifier.KeywordModifier>
    get() = modifiers.mapNotNull { it as? Node.Modifier.KeywordModifier }

val Node.Declaration.ClassDeclaration.isDataClass: Boolean
    get() = isClass && keywordModifiers.any { it is Node.Keyword.Data }
