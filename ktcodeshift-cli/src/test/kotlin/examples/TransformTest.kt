package examples

import ktcodeshift.testTransform
import kotlin.test.Test

class TransformTest {
    @Test
    fun testFlattenList() = testTransform(this::class, "FlattenList")
}
