import ktast.ast.Node
import ktcodeshift.*

transform { fileInfo ->
    Api
        .parse(fileInfo.source)
        .find<Node.ValueArgs>()
        .filter { _, p -> p is Node.Expression.Call && isListOf(p) }
        .replaceWith { v ->
            v.copy(elements = v.elements.flatMap { element ->
                val expr = element.expression
                if (expr is Node.Expression.Call && isListOf(expr)) {
                    expr.args?.elements ?: listOf()
                } else {
                    listOf(element)
                }
            })
        }
        .toSource()
}

fun isListOf(call: Node.Expression.Call): Boolean {
    val expr = call.expression as? Node.Expression.Name ?: return false
    return expr.name == "listOf"
}
