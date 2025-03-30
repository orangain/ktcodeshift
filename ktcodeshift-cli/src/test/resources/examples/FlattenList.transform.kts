transform { fileInfo ->
    Ktcodeshift
        .parse(fileInfo.source)
        .find<Node.Expression.CallExpression>()
        .filter { n -> isListOf(n) }
        .replaceWith { n ->
            n.copy(arguments = n.arguments.flatMap { argument ->
                val expr = argument.expression
                if (expr is Node.Expression.CallExpression && isListOf(expr)) {
                    expr.arguments
                } else {
                    listOf(argument)
                }
            })
        }
        .toSource()
}

fun isListOf(call: Node.Expression.CallExpression): Boolean {
    val expr = call.calleeExpression as? Node.Expression.NameExpression ?: return false
    return expr.text == "listOf"
}
