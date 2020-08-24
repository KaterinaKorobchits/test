package my.luckydog.common.extensions

import java.util.*

fun <T> List<T>.move(from: Int, to: Int) = apply {
    if (from < to) {
        (from until to).forEach { Collections.swap(this, it, it + 1) }
    } else (from downTo to + 1).forEach { Collections.swap(this, it, it - 1) }
}