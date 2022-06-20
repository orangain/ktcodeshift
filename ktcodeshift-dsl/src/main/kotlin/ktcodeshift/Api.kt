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
    val nodes = mutableListOf<T>()
    MutableVisitor.preVisit(fileNode, extrasMap) { v, _ ->
        if (v != null && v::class.java == javaClass) {
            nodes.add(v as T)
        }
        v
    }
    return NodeCollection(nodes.toList(), this)
}

data class NodeCollection<T : Node>(
    val nodes: List<T>,
    val fileWithContext: FileWithContext,
) {
    fun filter(predicate: (T) -> Boolean): NodeCollection<T> {
        return copy(
            nodes = nodes.filter(predicate)
        )
    }

    fun replaceWith(fn: (T) -> Node?): NodeCollection<T> = replaceWith { v, _ -> fn(v) }
    fun replaceWith(fn: (T, Node) -> Node?): NodeCollection<T> {
        val nodeMap = IdentityHashMap<T, Boolean>()
        nodes.forEach { node ->
            nodeMap[node] = true
        }
        val newFileNode = MutableVisitor.preVisit(fileWithContext.fileNode, fileWithContext.extrasMap) { v, parent ->
            if (nodeMap.contains(v)) {
                fn(v as T, parent)
            } else {
                v
            }
        }
        return copy(
            fileWithContext = FileWithContext(
                fileNode = newFileNode,
                extrasMap = fileWithContext.extrasMap,
            )
        )
    }

    fun toSource() = fileWithContext.toSource()
}
