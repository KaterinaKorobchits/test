package my.luckydog.common

operator fun String.times(other: String): String {
    return buildString {
        (0 until minOf(this@times.length, other.length)).forEach {
            append(this@times[it], other[it])
        }
    }
}