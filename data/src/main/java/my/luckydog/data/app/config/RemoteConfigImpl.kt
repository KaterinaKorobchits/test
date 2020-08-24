package my.luckydog.data.app.config

import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.get
import com.google.firebase.remoteconfig.ktx.remoteConfig
import io.reactivex.Completable
import io.reactivex.Single
import my.luckydog.boundaries.app.config.RemoteConfig
import my.luckydog.data.R
import javax.inject.Inject

class RemoteConfigImpl @Inject constructor() : RemoteConfig {

    init {
        println("!!!init: ${this::class.java.name} ${this.hashCode()}")
    }

    private val config: FirebaseRemoteConfig by lazy { Firebase.remoteConfig }

    override fun configure(): Completable = setDefaults().andThen { fetchAndActivate() }

    override fun getBoolean(key: String) = Single.just(config[key].asBoolean())

    override fun getDouble(key: String) = Single.just(config[key].asDouble())

    override fun getLong(key: String) = Single.just(config[key].asLong())

    override fun getString(key: String) = Single.just(config[key].asString())

    private fun setDefaults() = Completable.create { emitter ->
        config.setDefaultsAsync(R.xml.remote_config_defaults)
            .addOnSuccessListener { emitter.onComplete() }
            .addOnFailureListener { emitter.onError(it) }
    }

    private fun fetchAndActivate() = Completable.create { emitter ->
        config.fetch(0)
            .addOnSuccessListener { emitter.onComplete() }
            .addOnFailureListener { emitter.onError(it) }
    }.andThen {
        Completable.create { emitter ->
            config.activate()
                .addOnSuccessListener { emitter.onComplete() }
                .addOnFailureListener { emitter.onError(it) }
        }
    }
}