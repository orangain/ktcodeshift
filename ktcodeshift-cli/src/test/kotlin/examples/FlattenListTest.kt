package examples

import ktcodeshift.testTransform
import kotlin.test.Test

class FlattenListTest {
    @Test
    fun testFlattenList() = testTransform(this::class, "FlattenList")
}
