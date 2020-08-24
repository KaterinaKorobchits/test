package my.luckydog.domain.auth.usecases

import my.luckydog.domain.app.validators.EmailCaseImpl
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class EmailCaseImplTest(private val input: String, private val expected: Boolean) {

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data(): Collection<Array<Any>> {
            return listOf(
                arrayOf("", false),
                arrayOf(" ", false),
                arrayOf("aaa", false),
                arrayOf("aaa@", false),
                arrayOf("aaa@aaa", false),
                arrayOf("aaa@aaa@", false),
                arrayOf("aaa@aaa.aaa", true),
                arrayOf("11aaa@aaa.com", true),
                arrayOf("111", false),
                arrayOf("11aaa@", false),
                arrayOf("111@111", false),
                arrayOf("111@111@", false),
                arrayOf("aaa@@", false),
                arrayOf("11@aaa.ru", true)
            )
        }
    }

    @Test
    fun isValid() {
        assertEquals(expected, EmailCaseImpl().isValid(input))
    }
}