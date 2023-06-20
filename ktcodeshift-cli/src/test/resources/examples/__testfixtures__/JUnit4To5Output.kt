import org.junit.jupiter.api.*

class MyTestClass {
    companion object {
        @BeforeAll
        @JvmStatic
        fun setup() {
            // things to execute once and keep around for the class
        }

        @AfterAll
        @JvmStatic
        fun teardown() {
            // clean up after this class, leave nothing dirty behind
        }
    }

    @BeforeEach
    fun prepareTest() {
        // things to do before each test
    }

    @AfterEach
    fun cleanupTest() {
        // things to do after each test
    }

    @Disabled
    @Test
    fun testSkipped() {
        // skipped test case
    }

    @Test
    fun testRaises(){Assertions.assertThrows<IllegalStateException>{
        check(1 == 2)
    }}

    @Test
    fun testRaises2(){Assertions.assertThrows<java.lang.IllegalArgumentException>{
        require(1 == 2)
    }}

    @Test
    fun testSomething() {
        // an actual test case
    }

    @Test
    fun testSomethingElse() {
        // another test case
    }
}
