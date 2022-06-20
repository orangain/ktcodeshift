import ktast.ast.Node
import ktcodeshift.*

transform { fileInfo ->
    Api
        .parse(fileInfo.source)
        .find<Node.ValueArgs>()
        .replaceWith { v, p ->
            if (p is Node.Expr.Call && isListOf(p)) {
                v.copy(elements = v.elements.flatMap { element ->
                    val expr = element.expr
                    if (expr is Node.Expr.Call && isListOf(expr)) {
                        expr.args?.elements ?: listOf()
                    } else {
                        listOf(element)
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
