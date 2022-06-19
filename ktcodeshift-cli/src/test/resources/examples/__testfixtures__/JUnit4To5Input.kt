import org.junit.*

class MyTestClass {
    companion object {
        @BeforeClass
        @JvmStatic
        fun setup() {
            // things to execute once and keep around for the class
        }

        @AfterClass
        @JvmStatic
        fun teardown() {
            // clean up after this class, leave nothing dirty behind
        }
    }

    @Before
    fun prepareTest() {
        // things to do before each test
    }

    @After
    fun cleanupTest() {
        // things to do after each test
    }

    @Test
    fun testSomething() {
        // an actual test case
    }

    @Test
    fun testSomethingElse() {
        // another test case
    }
}
