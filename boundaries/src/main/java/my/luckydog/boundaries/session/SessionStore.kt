package my.luckydog.boundaries.session

interface SessionStore {

    fun getUserId(): Long

    fun storeUserId(userId: Long)

    fun clear()
}