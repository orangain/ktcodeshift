package ktcodeshift

import ktast.ast.MutableVisitor
import ktast.ast.Node
import ktast.ast.NodePath
import ktast.ast.Writer
import java.util.*
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

class NodeContext(path: NodePath<*>) {
    val parent: Node? = path.parent?.node
    val ancestors: Sequence<Node> = path.ancestors()
}

data class NodeCollection<T : Node>(
    val nodePaths: List<NodePath<T>>,
    val rootNode: Node.KotlinEntry,
) {
    fun filter(predicate: NodeContext.(T) -> Boolean): NodeCollection<T> {
        return filterIndexed { _, node -> predicate(node) }
    }

    fun filterIndexed(predicate: NodeContext.(Int, T) -> Boolean): NodeCollection<T> {
        return copy(
            nodePaths = nodePaths.filterIndexed { i, path -> NodeContext(path).predicate(i, path.node) }
        )
    }

    fun <S> map(fn: NodeContext.(T) -> S): List<S> {
        return mapIndexed { _, node -> fn(node) }
    }

    fun <S> mapIndexed(fn: NodeContext.(Int, T) -> S): List<S> {
        return nodePaths.mapIndexed { i, path -> NodeContext(path).fn(i, path.node) }
    }

    fun replaceWith(fn: NodeContext.(T) -> T): Node.KotlinEntry {
        val nodeMap = IdentityHashMap<T, Boolean>()
        nodePaths.forEach { nodeMap[it.node] = true }
        return MutableVisitor.traverse(rootNode) { path ->
            if (nodeMap.contains(path.node)) {
                @Suppress("UNCHECKED_CAST")
                NodeContext(path).fn(path.node as T)
            } else {
                path.node
            }
        }
    }
}
