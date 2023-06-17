import ktast.ast.Node
import ktcodeshift.*

transform { fileInfo ->
    Api
        .parse(fileInfo.source)
        .find<Node.Expression.CallExpression>()
        .filter { n -> isListOf(n) }
        .replaceWith { n ->
            n.copy(args = n.args.flatMap { arg ->
                val expr = arg.expression
                if (expr is Node.Expression.CallExpression && isListOf(expr)) {
                    expr.args
                } else {
                    listOf(arg)
                }
            })
        }
        .toSource()
}

fun isListOf(call: Node.Expression.CallExpression): Boolean {
    val expr = call.calleeExpression as? Node.Expression.NameExpression ?: return false
    return expr.text == "listOf"
}
