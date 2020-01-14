package my.luckydog

import io.reactivex.Single
import junit.framework.Assert.assertEquals
import org.junit.Test

class SingleErrorTest {

    companion object {
        private const val value = 1
        private val single = Single.just(value).map { it / 0 }
    }

    @Test
    fun `single with two consumers`() {
        single
            .test()

            .assertSubscribed()
            .assertError(java.lang.ArithmeticException::class.java)
            .assertNotComplete()
            .assertNoValues()
            .assertNever(value)
    }

    @Test
    fun `single order do operators with doAfterSuccess`() {
        val expectedOrder = "123"
        var actualOrder = ""

        single
            .doOnSubscribe { actualOrder += "1" }
            .doOnError{ actualOrder += "2" }
            .doOnTerminate { actualOrder += "3" }
            .doAfterSuccess { actualOrder += "4" }
            .test()

            .assertSubscribed()
            .assertError(java.lang.ArithmeticException::class.java)
            .assertNotComplete()
            .assertNoValues()
            .assertNever(value)

        assertEquals(expectedOrder, actualOrder)
    }

    @Test
    fun `single order do operators with doFinally`() {
        val expectedOrder = "1234"
        var actualOrder = ""

        single
            .doOnSubscribe { actualOrder += "1" }
            .doOnError { actualOrder += "2" }
            .doOnTerminate { actualOrder += "3" }
            .doFinally { actualOrder += "4" }
            .test()

            .assertSubscribed()
            .assertError(java.lang.ArithmeticException::class.java)
            .assertNotComplete()
            .assertNoValues()
            .assertNever(value)

        assertEquals(expectedOrder, actualOrder)
    }

    @Test
    fun `single order do operators with doAfterTerminate`() {
        val expectedOrder = "1234"
        var actualOrder = ""

        single
            .doOnSubscribe { actualOrder += "1" }
            .doOnError { actualOrder += "2" }
            .doOnTerminate { actualOrder += "3" }
            .doAfterTerminate { actualOrder += "4" }
            .test()

            .assertSubscribed()
            .assertError(java.lang.ArithmeticException::class.java)
            .assertNotComplete()
            .assertNoValues()
            .assertNever(value)

        assertEquals(expectedOrder, actualOrder)
    }

    @Test
    fun `single doOnSubscribe`() {
        val expected = "doOnSubscribe"
        var actual = ""

        single
            .doOnSubscribe { actual = expected }
            .test()

            .assertSubscribed()
            .assertError(java.lang.ArithmeticException::class.java)
            .assertNotComplete()
            .assertNoValues()
            .assertNever(value)

        assertEquals(expected, actual)
    }

    @Test
    fun `single doOnSuccess`() {
        val expected = ""
        var actual = ""

        single
            .doOnSuccess { actual = "doOnSuccess" }
            .test()

            .assertError(java.lang.ArithmeticException::class.java)
            .assertNotComplete()
            .assertNoValues()
            .assertNever(value)

        assertEquals(expected, actual)
    }

    @Test
    fun `single doOnError`() {
        val expected = "doOnError"
        var actual = ""

        single
            .doOnError { actual = expected }
            .test()

            .assertError(java.lang.ArithmeticException::class.java)
            .assertNotComplete()
            .assertNoValues()
            .assertNever(value)

        assertEquals(expected, actual)
    }

    @Test
    fun `single doOnTerminate`() {
        val expected = "doOnTerminate"
        var actual = ""

        single
            .doOnTerminate { actual = expected }
            .test()

            .assertError(java.lang.ArithmeticException::class.java)
            .assertNotComplete()
            .assertNoValues()
            .assertNever(value)
            .assertTerminated()

        assertEquals(expected, actual)
    }

    @Test
    fun `single doAfterSuccess`() {
        val expected = ""
        var actual = ""

        single
            .doAfterSuccess { actual = "doAfterSuccess" }
            .test()

            .assertError(java.lang.ArithmeticException::class.java)
            .assertNotComplete()
            .assertNoValues()
            .assertNever(value)

        assertEquals(expected, actual)
    }

    @Test
    fun `single doFinally`() {
        val expected = "doFinally"
        var actual = ""

        single
            .doFinally { actual = expected }
            .test()

            .assertError(java.lang.ArithmeticException::class.java)
            .assertNotComplete()
            .assertNoValues()
            .assertNever(value)

        assertEquals(expected, actual)
    }

    @Test
    fun `single doAfterTerminate`() {
        val expected = "doAfterTerminate"
        var actual = ""

        single
            .doAfterTerminate { actual = expected }
            .test()

            .assertError(java.lang.ArithmeticException::class.java)
            .assertNotComplete()
            .assertNoValues()
            .assertNever(value)

        assertEquals(expected, actual)
    }

    @Test
    fun `single onErrorReturn`() {
        single
            .onErrorReturn {
                if (it is ArithmeticException) -2 else -1
            }
            .test()

            .assertNoErrors()
            .assertComplete()
            .assertValue(-2)
    }

    @Test
    fun `single onErrorReturnItem`() {
        single
            .onErrorReturnItem(-1)
            .test()

            .assertNoErrors()
            .assertComplete()
            .assertValue(-1)
    }

    @Test
    fun `single onErrorResumeNext`() {
        single
            .onErrorResumeNext(Single.just(2))
            .test()

            .assertNoErrors()
            .assertComplete()
            .assertValue(2)
    }
}