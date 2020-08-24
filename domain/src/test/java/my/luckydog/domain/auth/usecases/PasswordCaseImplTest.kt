package my.luckydog.domain.auth.usecases

import my.luckydog.domain.app.validators.PasswordCaseImpl
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class PasswordCaseImplTest(private val input: String, private val expected: Boolean) {

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{index}: password {0} is {1}")
        fun data(): Collection<Array<Any>> {
            return listOf(
                arrayOf("", false),
                arrayOf(" ", false),
                arrayOf("    ", false),
                arrayOf("1", false),
                arrayOf("a1", false),
                arrayOf("as2", false),
                arrayOf("-*+", false),
                arrayOf("qqqq", true),
                arrayOf("111@111", true),
                arrayOf("----", true),
                arrayOf("+-.,", true),
                arrayOf("\t23rt", false),
                arrayOf("234567876543567890876543edfghjnbvc", true)
            )
        }
    }

    @Test
    fun isValid() {
        assertEquals(expected, PasswordCaseImpl().isValid(input))
    }
}