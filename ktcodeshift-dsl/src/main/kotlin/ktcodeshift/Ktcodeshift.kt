package ktcodeshift

import ktast.ast.Node
import ktast.ast.psi.Parser

object Ktcodeshift {
    fun parse(source: String): Node.KotlinFile {
        return Parser.parseFile(source)
    }
}
