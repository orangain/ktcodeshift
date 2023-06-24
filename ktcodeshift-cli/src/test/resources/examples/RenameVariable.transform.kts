import ktast.ast.Node
import ktcodeshift.*

transform { fileInfo ->
    Ktcodeshift
        .parse(fileInfo.source)
        .find<Node.Expression.NameExpression>()
        .filter { n ->
            parent is Node.Variable && n.text == "foo"
        }
        .replaceWith { n ->
            n.copy(text = "bar")
        }
        .toSource()
}
