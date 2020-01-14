package my.luckydog.data.session

import my.luckydog.boundaries.session.SessionStore
import my.luckydog.data.utils.PropertiesStore

class SessionStoreImpl(private val propertiesStore: PropertiesStore) :
    SessionStore {

    companion object {
        private const val USER_ID = "user_id"
        private const val DEFAULT_USER_ID = 0L
    }

    override fun getUserId() = propertiesStore.getLong(USER_ID)

    override fun storeUserId(userId: Long) = propertiesStore.putLong(USER_ID, userId)

    override fun clear() {
        storeUserId(DEFAULT_USER_ID)
    }
}