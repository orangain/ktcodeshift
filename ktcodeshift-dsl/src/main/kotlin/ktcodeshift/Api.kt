package ktcodeshift

import ktast.ast.MutableVisitor
import ktast.ast.Node
import ktast.ast.Writer
import ktast.ast.psi.ConverterWithMutableExtras
import ktast.ast.psi.Parser

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
