import kastree.ast.Node
import ktcodeshift.*

transform { fileInfo ->
    Api
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

fun isListOf(call: Node.Expr.Call): Boolean {
    val expr = call.expr
    return expr is Node.Expr.Name && expr.name == "listOf"
}
