package ktcodeshift

import kotlinx.ast.common.AstSource
import kotlinx.ast.common.ast.Ast
import kotlinx.ast.grammar.kotlin.target.antlr.kotlin.KotlinGrammarAntlrKotlinParser

interface Transform {
    fun transform(fileInfo: FileInfo, api: Api/*, options: Options*/): String
}

interface FileInfo {
    val path: String
    val source: String
}

//interface Api {
//
//}

class Api {
    fun parse(source: String): Ast {
        val astSource = AstSource.String("file", source)
        val ast = KotlinGrammarAntlrKotlinParser.parseKotlinFile(astSource)
        return ast
    }
}

//interface Options {
//
//}