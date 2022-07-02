import ktast.ast.Node
import ktcodeshift.Api
import ktcodeshift.find
import ktcodeshift.toSource
import ktcodeshift.transform

transform { fileInfo ->
    Api
        .parse(fileInfo.source)
        .find<Node.ValueArgs>()
        .filter { _, p -> p is Node.Expr.Call && isListOf(p) }
        .replaceWith { v ->
            v.copy(elements = v.elements.flatMap { element ->
                val expr = element.expr
                if (expr is Node.Expr.Call && isListOf(expr)) {
                    expr.args?.elements ?: listOf()
                } else {
                    listOf(element)
                }
            })
        }
        .toSource()
}

fun isListOf(call: Node.Expr.Call): Boolean {
    val expr = call.expr
    return expr is Node.Expr.Name && expr.name == "listOf"
}
