package my.luckydog.data.app.session

import my.luckydog.boundaries.app.session.SessionStore
import my.luckydog.common.PropertiesStore

class SessionStoreImpl(private val propertiesStore: PropertiesStore) : SessionStore {

    companion object {
        private const val USER_ID = "user_id"
        private const val DEFAULT_USER_ID = 0L
    }

    init {
        println("!!!init: ${this::class.java.name} ${this.hashCode()}")
    }

    override fun getUserId() = propertiesStore.getLong(USER_ID)

    override fun storeUserId(userId: Long) = propertiesStore.putLong(USER_ID, userId)

    override fun clear() {
        storeUserId(DEFAULT_USER_ID)
    }
}