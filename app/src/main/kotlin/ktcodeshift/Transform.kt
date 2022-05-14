package ktcodeshift

import kastree.ast.Node
import kastree.ast.psi.Parser

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
    fun parse(source: String): Node.File {
        return Parser.parseFile(source)
    }
}

//interface Options {
//
//}