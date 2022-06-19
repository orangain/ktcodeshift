package ktcodeshift

import ktast.ast.MutableVisitor
import ktast.ast.Node
import ktast.ast.Writer
import ktast.ast.psi.ConverterWithExtras
import ktast.ast.psi.Parser

object Api {
    fun parse(source: String): FileWithContext {
        val extrasMap = ConverterWithExtras()
        val parser = Parser(extrasMap)
        return FileWithContext(parser.parseFile(source), extrasMap)
    }
}

data class FileWithContext(
    val fileNode: Node.File,
    val extrasMap: ConverterWithExtras,
)

fun FileWithContext.preVisit(fn: (Node?, Node) -> Node?): FileWithContext {
    val changedFileNode = MutableVisitor.preVisit(fileNode, fn)
    println(fileNode === changedFileNode)
    return copy(fileNode = changedFileNode)
}

fun FileWithContext.toSource(): String {
    println(extrasMap)
    return Writer.write(fileNode, extrasMap)
}
