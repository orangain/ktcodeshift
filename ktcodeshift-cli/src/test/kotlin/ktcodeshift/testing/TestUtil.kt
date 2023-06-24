package ktcodeshift.testing

import ktcodeshift.*
import kotlin.io.path.Path
import kotlin.reflect.KClass
import kotlin.script.experimental.host.UrlScriptSource
import kotlin.test.assertEquals

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
        override val path = Path(inputPath)
        override val source = inputSource
    })

    assertEquals(outputSource.trim(), changedSource.trim())
}
