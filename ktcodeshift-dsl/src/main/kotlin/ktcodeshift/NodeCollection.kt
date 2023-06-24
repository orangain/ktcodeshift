package ktcodeshift

import ktast.ast.MutableVisitor
import ktast.ast.Node
import ktast.ast.NodePath
import java.util.*

/**
 * A collection of nodes filtered under the root node.
 *
 * @property nodePaths the list of node paths.
 * @property rootNode the root node.
 */
data class NodeCollection<T : Node>(
    val nodePaths: List<NodePath<T>>,
    val rootNode: Node.KotlinEntry,
) {
    /**
     * Returns a new collection containing only elements matching the given [predicate].
     */
    fun filter(predicate: NodeContext.(T) -> Boolean): NodeCollection<T> {
        return filterIndexed { _, node -> predicate(node) }
    }

    /**
     * Returns a new collection containing only elements matching the given [predicate]. The element index is passed as the first argument to the predicate.
     */
    fun filterIndexed(predicate: NodeContext.(Int, T) -> Boolean): NodeCollection<T> {
        return copy(
            nodePaths = nodePaths.filterIndexed { i, path -> NodeContext(path).predicate(i, path.node) }
        )
    }

    /**
     * Returns a list of the results of applying the given [transform] function to each element in the original collection.
     */
    fun <S> map(transform: NodeContext.(T) -> S): List<S> {
        return mapIndexed { _, node -> transform(node) }
    }

    /**
     * Returns a list of the results of applying the given [transform] function to each element in the original collection. The element index is passed as the first argument to the transform function.
     */
    fun <S> mapIndexed(transform: NodeContext.(Int, T) -> S): List<S> {
        return nodePaths.mapIndexed { i, path -> NodeContext(path).transform(i, path.node) }
    }

    /**
     * Returns a new AST root node with the nodes in this collection replaced with the result of the given [transform] function applied to each node.
     */
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

/**
 * Context for a node.
 *
 * @param path the node path.
 * @property parent the parent node.
 * @property ancestors a sequence of the ancestor nodes, starting from the parent node and ending with the root node.
 */
class NodeContext(path: NodePath<*>) {
    val parent: Node? = path.parent?.node
    val ancestors: Sequence<Node> = path.ancestors()
}
