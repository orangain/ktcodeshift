package ktcodeshift

import kotlin.script.experimental.host.UrlScriptSource
import kotlin.test.Test
import kotlin.test.assertEquals

class TransformTest {
    @Test
    fun testTransform() {
        val scriptUrl = this.javaClass.classLoader.getResource("examples/FlattenList.transform.kts")
        val transform = evalScriptSource(UrlScriptSource(scriptUrl))
        val changedSource = applyTransform(transform, object : FileInfo {
            override val path = "Test.kt"
            override val source = """
                package testing
                
                val a = listOf(listOf(1, 2), listOf(3))
            """.trimIndent()
        })

        assertEquals("""
            package testing
            
            val a = listOf(1, 2, 3)
        """.trimIndent().trim(), changedSource.trim())
    }
}
