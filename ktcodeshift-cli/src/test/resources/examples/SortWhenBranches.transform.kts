/*
You can sort when branches in ../ktast/ast/src/commonMain/kotlin/ktast/ast/{Visitor,MutableVisitor,Writer}.kt using the following command:

ktcodeshift -t ktcodeshift-cli/src/test/resources/examples/SortWhenBranches.transform.kts ../ktast/ast/src/commonMain/kotlin/ktast/ast

*/

import ktast.ast.Node
import ktcodeshift.*
import java.nio.charset.StandardCharsets

transform { fileInfo ->
    val fileName = fileInfo.path.fileName.toString()
    println("fileName: $fileName")
    if (!setOf("Visitor.kt", "MutableVisitor.kt", "Writer.kt").contains(fileName)) {
        return@transform null
    }

    val nodeSource =
        java.io.File("../ktast/ast/src/commonMain/kotlin/ktast/ast/Node.kt").readText(StandardCharsets.UTF_8)
    val nodeIndexes = Ktcodeshift.parse(nodeSource)
        .find<Node.Declaration.ClassDeclaration>()
        .map { nestedClassNames(it, ancestors) }
        .mapIndexed { index, names -> names to index }
        .toMap()
//    println(nodeIndexes)

    Ktcodeshift
        .parse(fileInfo.source)
        .find<Node.Expression.WhenExpression>()
        .filterIndexed { index, _ ->
            index == 0
        }
        .replaceWith { node ->
            node.copy(
                branches = node.branches.sortedBy { branch ->
                    if (branch is Node.Expression.WhenExpression.ElseWhenBranch) {
                        return@sortedBy Int.MAX_VALUE
                    }
                    val type = branch.conditions[0].type as Node.Type.SimpleType
                    val names = type.qualifiers.map { it.name.text } + type.name.text
                    nodeIndexes[names] ?: 0
                }
            )
        }
        .toSource()
}

fun nestedClassNames(node: Node, ancestors: Sequence<Node>): List<String> {
    val nestedClasses = (ancestors.toList().reversed() + node)
        .filterIsInstance<Node.Declaration.ClassDeclaration>()
    return nestedClasses.map { it.name.text }
}
