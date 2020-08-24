package my.luckydog.boundaries.app.config

interface RemoteConfigStore {

    fun storeTranslateKey(key: String)

    fun getTranslateKey(): String?
}