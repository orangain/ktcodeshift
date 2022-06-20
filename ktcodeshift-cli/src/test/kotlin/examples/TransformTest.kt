package examples

import ktcodeshift.testTransform
import kotlin.test.Test

class TransformTest {
    @Test
    fun testFlattenList() = testTransform(this::class, "FlattenList")

    @Test
    fun testJUnit4To5() = testTransform(this::class, "JUnit4To5")
}