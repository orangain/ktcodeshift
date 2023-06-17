package ktcodeshift

import ktast.ast.MutableVisitor
import ktast.ast.Node
import ktast.ast.NodePath
import ktast.ast.Writer
import ktast.ast.psi.ConverterWithMutableExtras
import ktast.ast.psi.Parser
import java.util.*
import kotlin.reflect.KClass

object Api {
    fun parse(source: String): FileWithContext {
        val extrasMap = ConverterWithMutableExtras()
        val parser = Parser(extrasMap)
        return FileWithContext(parser.parseFile(source), extrasMap)
    }
}

data class FileWithContext(
    val fileNode: Node.KotlinFile,
    val extrasMap: ConverterWithMutableExtras,
)

fun FileWithContext.traverse(fn: (path: NodePath<*>) -> Node): FileWithContext {
    val changedFileNode = MutableVisitor.traverse(fileNode, extrasMap, fn)
    return copy(fileNode = changedFileNode)
}

fun FileWithContext.toSource(): String {
    return Writer.write(fileNode, extrasMap)
}

inline fun <reified T : Node> FileWithContext.find(): NodeCollection<T> = find(T::class)

fun <T : Node> FileWithContext.find(kClass: KClass<T>): NodeCollection<T> = find(kClass.java)

fun <T : Node> FileWithContext.find(javaClass: Class<T>): NodeCollection<T> {
    val nodes = mutableListOf<NodePath<T>>()
    MutableVisitor.traverse(fileNode, extrasMap) { path ->
        if (path.node::class.java == javaClass) {
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
    val fileWithContext: FileWithContext,
) {
    fun filter(predicate: NodeContext.(T) -> Boolean): NodeCollection<T> {
        return copy(
            nodePaths = nodePaths.filter { NodeContext(it).predicate(it.node) }
        )
    }

    fun replaceWith(fn: NodeContext.(T) -> T): FileWithContext {
        val nodeMap = IdentityHashMap<T, Boolean>()
        nodePaths.forEach { nodeMap[it.node] = true }
        val newFileNode = MutableVisitor.traverse(fileWithContext.fileNode, fileWithContext.extrasMap) { path ->
            if (nodeMap.contains(path.node)) {
                @Suppress("UNCHECKED_CAST")
                NodeContext(path).fn(path.node as T)
            } else {
                path.node
            }
        }
        return FileWithContext(
            fileNode = newFileNode,
            extrasMap = fileWithContext.extrasMap,
        )
    }
}
