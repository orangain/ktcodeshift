package ktcodeshift

import kastree.ast.MutableVisitor
import kastree.ast.Node
import kastree.ast.Writer
import kastree.ast.psi.Parser

class Api {
    fun parse(source: String): Node.File {
        return Parser.parseFile(source)
    }
}

fun <T: Node> T.preVisit(fn: (Node?, Node) -> Node?): T {
    return MutableVisitor.preVisit(this, fn)
}

fun <T: Node> T.toSource(): String {
    return Writer.write(this)
}