package my.luckydog.presentation.core

open class Event<out T>(private val content: T?) {

    var isHandled: Boolean = false
        private set

    fun getContent(): T? {
        return if (isHandled) null else {
            isHandled = true
            content
        }
    }
}