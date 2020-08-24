package my.luckydog.presentation.core

interface DrawerManager {

    fun setEmai(email: String)

    fun isOpen(): Boolean

    fun close()

    fun open()
}