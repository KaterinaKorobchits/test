package my.luckydog.data.app.auth

import my.luckydog.boundaries.app.auth.AuthStore
import my.luckydog.common.PropertiesStore

class AuthStoreImpl(private val propertiesStore: PropertiesStore) : AuthStore {

    companion object {
        private const val HAS_AUTHORIZE = "has _authorize"
    }

    init {
        println("!!!init: ${this::class.java.name} ${this.hashCode()}")
    }

    override fun hasAuthorize() = propertiesStore.getBoolean(HAS_AUTHORIZE)

    override fun storeHasAuthorize(hasAuthorize: Boolean) =
        propertiesStore.putBoolean(HAS_AUTHORIZE, hasAuthorize)
}