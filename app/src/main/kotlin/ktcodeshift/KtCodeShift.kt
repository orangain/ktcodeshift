package ktcodeshift

import kotlinx.ast.common.AstSource
import kotlinx.ast.common.ast.Ast
import kotlinx.ast.grammar.kotlin.target.antlr.kotlin.KotlinGrammarAntlrKotlinParser

fun applyTransform(transform: Transform, fileInfo: FileInfo): String {
    val api = Api()
    return transform.transform(fileInfo, api)
}

