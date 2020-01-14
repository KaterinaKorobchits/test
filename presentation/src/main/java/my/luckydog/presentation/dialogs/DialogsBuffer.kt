package my.luckydog.presentation.dialogs

interface DialogsBuffer {

    fun getFirst(): DialogParams

    fun getFirstId(): String

    fun add(id: String, params: DialogParams)

    fun remove(id: String)

    fun isContain(id: String): Boolean

    fun isNotEmpty(): Boolean

    fun clear()
}