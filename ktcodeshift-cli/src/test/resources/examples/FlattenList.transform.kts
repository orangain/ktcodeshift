import ktast.ast.Node
import ktcodeshift.*

transform { fileInfo ->
    Api
        .parse(fileInfo.source)
        .preVisit { v, p ->
            if (v is Node.ValueArgs && p is Node.Expr.Call && isListOf(p)) {
                v.copy(args = v.args.flatMap { arg ->
                    val expr = arg.expr
                    if (expr is Node.Expr.Call && isListOf(expr)) {
                        expr.args?.args ?: listOf()
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
