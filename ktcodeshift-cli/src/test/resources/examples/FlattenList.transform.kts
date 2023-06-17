import ktast.ast.Node
import ktcodeshift.*

transform { fileInfo ->
    Api
        .parse(fileInfo.source)
        .find<Node.Expression.CallExpression>()
        .filter { n -> isListOf(n) }
        .replaceWith { n ->
            n.copy(args = n.args.flatMap { element ->
                val expr = element.expression
                if (expr is Node.Expression.CallExpression && isListOf(expr)) {
                    expr.args
                } else {
                    listOf(element)
                }
            })
        }
        .toSource()
}

fun isListOf(e: Node.Expression.CallExpression): Boolean {
    val expr = e.calleeExpression as? Node.Expression.NameExpression ?: return false
    return expr.text == "listOf"
}
