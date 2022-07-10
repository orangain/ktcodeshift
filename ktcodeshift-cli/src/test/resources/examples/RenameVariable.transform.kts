import ktast.ast.Node
import ktcodeshift.Api
import ktcodeshift.find
import ktcodeshift.toSource
import ktcodeshift.transform

transform { fileInfo ->
    Api
        .parse(fileInfo.source)
        .find<Node.Expression.Name>()
        .filter { v, parent ->
            parent is Node.Declaration.Property.Variable && v.name == "foo"
        }
        .replaceWith { v ->
            v.copy(name = "bar")
        }
        .toSource()
}
