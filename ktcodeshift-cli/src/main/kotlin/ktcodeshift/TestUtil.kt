package ktcodeshift

import kotlin.reflect.KClass
import kotlin.script.experimental.host.UrlScriptSource

fun testTransform(testClass: KClass<*>, transformName: String, fixtureName: String = transformName) {
    val packageName = testClass.java.packageName
    val packageDir = packageName.replace('.', '/')
    val transformPath = "${packageDir}/${transformName}.transform.kts"
    val inputPath = "${packageDir}/__testfixtures__/${fixtureName}Input.kt"
    val outputPath = "${packageDir}/__testfixtures__/${fixtureName}Output.kt"
    val scriptUrl = testClass.java.classLoader.getResource(transformPath)
    val inputSource = testClass.java.classLoader.getResource(inputPath).readText()
    val outputSource = testClass.java.classLoader.getResource(outputPath).readText()
    val transform = evalScriptSource(UrlScriptSource(scriptUrl))

    val changedSource = applyTransform(transform, object : FileInfo {
        override val path = inputPath
        override val source = inputSource
    })

    assert(changedSource.trim() == outputSource.trim()) {
        """
changedSource does not match outputSource.

changedSource:
---------------------------------------------------
${changedSource.trim()}
---------------------------------------------------

outputSource:
---------------------------------------------------
${outputSource.trim()}
---------------------------------------------------
""".trimIndent()
    }
}
