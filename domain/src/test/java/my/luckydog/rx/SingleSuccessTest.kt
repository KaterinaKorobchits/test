package my.luckydog.rx

import io.reactivex.Single
import junit.framework.Assert.assertEquals
import org.junit.Test

class SingleSuccessTest {

    companion object {
        private const val value = 1
        private val single = Single.just(value)
    }

    @Test
    fun `single with two consumers`() {
        single
            .test()

            .assertSubscribed()
            .assertNoErrors()
            .assertComplete()
            .assertValue(value)
    }

    @Test
    fun `single order do operators with doAfterSuccess`() {
        val expectedOrder = "1234"
        var actualOrder = ""

        single
            .doOnSubscribe { actualOrder += "1" }
            .doOnSuccess { actualOrder += "2" }
            .doOnTerminate { actualOrder += "3" }
            .doAfterSuccess { actualOrder += "4" }
            .test()

            .assertNoErrors()
            .assertComplete()
            .assertValue(value)

        assertEquals(expectedOrder, actualOrder)
    }

    @Test
    fun `single order do operators with doFinally`() {
        val expectedOrder = "1234"
        var actualOrder = ""

        single
            .doOnSubscribe { actualOrder += "1" }
            .doOnSuccess { actualOrder += "2" }
            .doOnTerminate { actualOrder += "3" }
            .doFinally { actualOrder += "4" }
            .test()

            .assertNoErrors()
            .assertComplete()
            .assertValue(value)

        assertEquals(expectedOrder, actualOrder)
    }

    @Test
    fun `single order do operators with doAfterTerminate`() {
        val expectedOrder = "1234"
        var actualOrder = ""

        single
            .doOnSubscribe { actualOrder += "1" }
            .doOnSuccess { actualOrder += "2" }
            .doOnTerminate { actualOrder += "3" }
            .doAfterTerminate { actualOrder += "4" }
            .test()

            .assertNoErrors()
            .assertComplete()
            .assertValue(value)

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
            .assertNoErrors()
            .assertComplete()
            .assertValue(value)

        assertEquals(expected, actual)
    }

    @Test
    fun `single doOnSuccess`() {
        val expected = "doOnSuccess"
        var actual = ""

        single
            .doOnSuccess { actual = expected }
            .test()

            .assertNoErrors()
            .assertComplete()
            .assertValue(value)

        assertEquals(expected, actual)
    }

    @Test
    fun `single doOnError`() {
        val expected = ""
        var actual = ""

        single
            .doOnError { actual = "doOnError" }
            .test()

            .assertNoErrors()
            .assertComplete()
            .assertValue(value)

        assertEquals(expected, actual)
    }

    @Test
    fun `single doOnTerminate`() {
        val expected = "doOnTerminate"
        var actual = ""

        single
            .doOnTerminate { actual = expected }
            .test()

            .assertNoErrors()
            .assertComplete()
            .assertTerminated()
            .assertValue(value)

        assertEquals(expected, actual)
    }

    @Test
    fun `single doAfterSuccess`() {
        val expected = "doAfterSuccess"
        var actual = ""

        single
            .doAfterSuccess { actual = expected }
            .test()

            .assertNoErrors()
            .assertComplete()
            .assertValue(value)

        assertEquals(expected, actual)
    }

    @Test
    fun `single doFinally`() {
        val expected = "doFinally"
        var actual = ""

        single
            .doFinally { actual = expected }
            .test()

            .assertNoErrors()
            .assertComplete()
            .assertValue(value)

        assertEquals(expected, actual)
    }

    @Test
    fun `single doAfterTerminate`() {
        val expected = "doAfterTerminate"
        var actual = ""

        single
            .doAfterTerminate { actual = expected }
            .test()

            .assertNoErrors()
            .assertComplete()
            .assertValue(value)

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
            .assertValue(value)
    }

    @Test
    fun `single onErrorReturnItem`() {
        single
            .onErrorReturnItem(-1)
            .test()

            .assertNoErrors()
            .assertComplete()
            .assertValue(value)
    }

    @Test
    fun `single onErrorResumeNext`() {
        single
            .onErrorResumeNext(Single.just(2))
            .test()

            .assertNoErrors()
            .assertComplete()
            .assertValue(value)
    }
}