package ktcodeshift

import ktast.ast.Node
import ktast.ast.psi.ConverterWithMutableExtras
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class FileWithContextTest {

    private val fileWithContext = FileWithContext(
        kotlinFile(
            declarations = listOf(
                classDeclaration(
                    declarationKeyword = Node.Keyword.Class(),
                    name = nameExpression("Foo"),
                ),
                objectDeclaration(
                    name = nameExpression("Bar")
                ),
                functionDeclaration(
                    name = nameExpression("baz")
                )
            )
        ),
        ConverterWithMutableExtras()
    )

    @Test
    fun testFindByTypeArgument() {
        val result = fileWithContext.find<Node.Declaration.ClassDeclaration>()
        assertEquals(1, result.nodePaths.size)
        assertIs<Node.Declaration.ClassDeclaration>(result.nodePaths[0].node)
    }

    @Test
    fun testFindByKClass() {
        val result = fileWithContext.find(Node.Declaration.ClassDeclaration::class)
        assertEquals(1, result.nodePaths.size)
        assertIs<Node.Declaration.ClassDeclaration>(result.nodePaths[0].node)
    }

    @Test
    fun testFindByJavaClass() {
        val result = fileWithContext.find(Node.Declaration.ClassDeclaration::class.java)
        assertEquals(1, result.nodePaths.size)
        assertIs<Node.Declaration.ClassDeclaration>(result.nodePaths[0].node)
    }

    @Test
    fun testFindByParent() {
        val result = fileWithContext.find<Node.Declaration.ClassOrObject>()
        assertEquals(2, result.nodePaths.size)
    }
}

class NodeCollectionTest {
    private val nodeCollection = FileWithContext(
        kotlinFile(
            declarations = listOf(
                classDeclaration(
                    declarationKeyword = Node.Keyword.Class(),
                    name = nameExpression("Foo"),
                ),
                objectDeclaration(
                    name = nameExpression("Bar")
                ),
                functionDeclaration(
                    name = nameExpression("baz")
                )
            )
        ),
        ConverterWithMutableExtras()
    ).find<Node.Declaration>()

    @Test
    fun testFilter() {
        assertEquals(3, nodeCollection.nodePaths.size)

        val result = nodeCollection.filter { it is Node.Declaration.ClassOrObject }
        assertEquals(2, result.nodePaths.size)
    }

    @Test
    fun testFilterIndexed() {
        assertEquals(3, nodeCollection.nodePaths.size)

        var expectedIndex = 0
        val result = nodeCollection.filterIndexed { i, node ->
            assertEquals(expectedIndex++, i)
            node is Node.Declaration.ClassOrObject
        }
        assertEquals(2, result.nodePaths.size)
        assertEquals(3, expectedIndex)
    }

    @Test
    fun testMap() {
        assertEquals(3, nodeCollection.nodePaths.size)

        val result = nodeCollection.map { it is Node.Declaration.ClassOrObject }
        assertEquals(listOf(true, true, false), result)
    }

    @Test
    fun testMapIndexed() {
        assertEquals(3, nodeCollection.nodePaths.size)

        val result = nodeCollection.mapIndexed { i, node ->
            Pair(i, node is Node.Declaration.ClassOrObject)
        }
        assertEquals(
            listOf(
                Pair(0, true),
                Pair(1, true),
                Pair(2, false),
            ), result
        )
    }
}
