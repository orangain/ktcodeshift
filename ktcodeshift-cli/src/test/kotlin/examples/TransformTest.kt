package examples

import ktcodeshift.testing.testTransform
import kotlin.test.Test

class TransformTest {
    @Test
    fun testFlattenList() = testTransform(this::class, "FlattenList")

    @Test
    fun testJUnit4To5() = testTransform(this::class, "JUnit4To5")

    @Test
    fun testRenameVariable() = testTransform(this::class, "RenameVariable")

    @Test
    fun testAnnotations() = testTransform(this::class, "Annotations")
}
