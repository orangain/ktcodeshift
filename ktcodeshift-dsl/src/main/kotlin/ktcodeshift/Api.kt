package ktcodeshift

import ktast.ast.MutableVisitor
import ktast.ast.Node
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
    val fileNode: Node.File,
    val extrasMap: ConverterWithMutableExtras,
)

fun FileWithContext.preVisit(fn: (Node?, Node) -> Node?): FileWithContext {
    val changedFileNode = MutableVisitor.preVisit(fileNode, extrasMap, fn)
    return copy(fileNode = changedFileNode)
}

fun FileWithContext.toSource(): String {
    return Writer.write(fileNode, extrasMap)
}

inline fun <reified T : Node> FileWithContext.find(): NodeCollection<T> = find(T::class)

fun <T : Node> FileWithContext.find(kClass: KClass<T>): NodeCollection<T> = find(kClass.java)

fun <T : Node> FileWithContext.find(javaClass: Class<T>): NodeCollection<T> {
    val nodes = mutableListOf<NodeAndParent<T>>()
    MutableVisitor.preVisit(fileNode, extrasMap) { v, parent ->
        if (v != null && v::class.java == javaClass) {
            nodes.add(NodeAndParent(v as T, parent))
        }
        v
    }
    return NodeCollection(nodes.toList(), this)
}

data class NodeAndParent<T : Node>(
    val node: T,
    val parent: Node,
)

data class NodeCollection<T : Node>(
    val nodeAndParents: List<NodeAndParent<T>>,
    val fileWithContext: FileWithContext,
) {
    fun filter(predicate: (T) -> Boolean): NodeCollection<T> = filter { v, _ -> predicate(v) }
    fun filter(predicate: (T, Node) -> Boolean): NodeCollection<T> {
        return copy(
            nodeAndParents = nodeAndParents.filter { predicate(it.node, it.parent) }
        )
    }

    fun replaceWith(fn: (T) -> Node?): FileWithContext = replaceWith { v, _ -> fn(v) }
    fun replaceWith(fn: (T, Node) -> Node?): FileWithContext {
        val nodeMap = IdentityHashMap<T, Boolean>()
        nodeAndParents.forEach { nodeMap[it.node] = true }
        val newFileNode = MutableVisitor.preVisit(fileWithContext.fileNode, fileWithContext.extrasMap) { v, parent ->
            if (nodeMap.contains(v)) {
                fn(v as T, parent)
            } else {
                v
            }
        }
        return FileWithContext(
            fileNode = newFileNode,
            extrasMap = fileWithContext.extrasMap,
        )
    }
}
