package ktcodeshift

import ktast.ast.Node
import ktast.ast.psi.Parser

/**
 * The main entry point for the ktcodeshift API.
 */
object Ktcodeshift {
    /**
     * Parses the given source code into a Kotlin AST.
     */
    fun parse(source: String): Node.KotlinFile {
        return Parser.parseFile(source)
    }
}
