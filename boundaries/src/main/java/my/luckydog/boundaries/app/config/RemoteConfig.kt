package my.luckydog.boundaries.app.config

import io.reactivex.Completable
import io.reactivex.Single

interface RemoteConfig {

    fun configure(): Completable

    fun getBoolean(key: String): Single<Boolean>

    fun getDouble(key: String): Single<Double>

    fun getLong(key: String): Single<Long>

    fun getString(key: String): Single<String>
}