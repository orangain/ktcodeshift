package ktcodeshift

import ktast.ast.MutableVisitor
import ktast.ast.Node
import ktast.ast.NodePath
import ktast.ast.Writer
import kotlin.reflect.KClass

/**
 * Converts the given node to source code.
 */
fun Node.toSource(): String {
    return Writer.write(this)
}

/**
 * Returns [NodeCollection] of all nodes of type [T] under this node.
 */
inline fun <reified T : Node> Node.KotlinEntry.find(): NodeCollection<T> = find(T::class)

/**
 * Returns [NodeCollection] of all nodes of type [T] under this node.
 */
fun <T : Node> Node.KotlinEntry.find(kClass: KClass<T>): NodeCollection<T> = find(kClass.java)

/**
 * Returns [NodeCollection] of all nodes of type [T] under this node.
 */
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

/**
 * Get list of annotations for this node.
 */
val Node.WithModifiers.annotations: List<Node.Modifier.AnnotationSet.Annotation>
    get() = annotationSets.flatMap { it.annotations }

/**
 * Get list of keyword modifiers for this node.
 */
val Node.WithModifiers.keywordModifiers: List<Node.Modifier.KeywordModifier>
    get() = modifiers.mapNotNull { it as? Node.Modifier.KeywordModifier }

/**
 * Returns true if this node is a data class.
 */
val Node.Declaration.ClassDeclaration.isDataClass: Boolean
    get() = isClass && keywordModifiers.any { it is Node.Keyword.Data }
