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
        val classOrObjects = fileWithContext.find<Node.Declaration.ClassOrObject>()
        assertEquals(2, classOrObjects.nodePaths.size)
    }
}
