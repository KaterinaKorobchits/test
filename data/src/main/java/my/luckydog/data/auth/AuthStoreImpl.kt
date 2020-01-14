package my.luckydog.data.auth

import my.luckydog.boundaries.auth.AuthStore
import my.luckydog.data.utils.PropertiesStore

class AuthStoreImpl(private val propertiesStore: PropertiesStore) :
    AuthStore {

    companion object {
        private const val HAS_AUTHORIZE = "has _authorize"
    }

    override fun hasAuthorize() = propertiesStore.getBoolean(HAS_AUTHORIZE)

    override fun storeHasAuthorize(hasAuthorize: Boolean) = propertiesStore.putBoolean(HAS_AUTHORIZE, hasAuthorize)
}