package my.luckydog.boundaries.app.session

interface SessionStore {

    fun getUserId(): Long

    fun storeUserId(userId: Long)

    fun clear()
}