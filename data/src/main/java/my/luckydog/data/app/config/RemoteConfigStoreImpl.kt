package my.luckydog.data.app.config

import my.luckydog.boundaries.app.config.RemoteConfigStore
import javax.inject.Inject

class RemoteConfigStoreImpl @Inject constructor() :
    RemoteConfigStore {

    private companion object {
        private const val TRANSLATE_KEY = "translate_key"
    }

    private val map = HashMap<String, Any>()

    override fun getTranslateKey(): String? = map[TRANSLATE_KEY] as? String

    override fun storeTranslateKey(key: String) {
        map[TRANSLATE_KEY] = key
    }
}