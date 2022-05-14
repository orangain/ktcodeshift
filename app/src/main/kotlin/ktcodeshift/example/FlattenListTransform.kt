package ktcodeshift.example

import kastree.ast.Node
import ktcodeshift.*

class FlattenListTransform : Transform {
    override fun transform(fileInfo: FileInfo, api: Api): String {
        return api
            .parse(fileInfo.source)
            .preVisit { v, _ ->
                if (v is Node.Expr.Call && isListOf(v)) {
                    v.copy(args = v.args.flatMap { arg ->
                        val expr = arg.expr
                        if (expr is Node.Expr.Call && isListOf(expr)) {
                            expr.args
                        } else {
                            listOf(arg)
                        }
                    })
                } else {
                    v
                }
            }
            .toSource()
    }
}

private fun isListOf(call: Node.Expr.Call): Boolean {
    val expr = call.expr
    return expr is Node.Expr.Name && expr.name == "listOf"
}