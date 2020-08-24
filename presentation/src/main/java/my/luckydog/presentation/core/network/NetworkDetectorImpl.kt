package my.luckydog.presentation.core.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities.TRANSPORT_CELLULAR
import android.net.NetworkCapabilities.TRANSPORT_WIFI
import android.net.NetworkRequest
import android.os.Build
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import my.luckydog.presentation.core.extensions.getConnectivityManager
import javax.inject.Inject

class NetworkDetectorImpl @Inject constructor() : NetworkDetector {

    init {
        println("!!!init: ${this::class.java.name} ${this.hashCode()}")
    }

    override fun observe(context: Context): Flow<Boolean> {
        val manager = context.applicationContext.getConnectivityManager()

        return callbackFlow {
            val callback = object : ConnectivityManager.NetworkCallback() {

                override fun onAvailable(network: Network) {
                    offer(true)
                }

                override fun onLost(network: Network) {
                    offer(false)
                }
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                manager.registerDefaultNetworkCallback(callback)
            } else {
                val builder = NetworkRequest.Builder()
                    .addTransportType(TRANSPORT_CELLULAR)
                    .addTransportType(TRANSPORT_WIFI)
                manager.registerNetworkCallback(builder.build(), callback)
            }
            offer(manager.activeNetworkInfo?.isConnected ?: false)

            awaitClose { manager.unregisterNetworkCallback(callback) }

        }.distinctUntilChanged()
    }
}

/*class NetworkDetectorImpl @Inject constructor() : NetworkDetector {

    init {
        println("!!!init: ${this::class.java.name} ${this.hashCode()}")
    }

    override fun observe(context: Context): Observable<Boolean> {
        val manager = context.applicationContext.getConnectivityManager()

        return Observable.create<Boolean> { emitter ->
            val callback = object : ConnectivityManager.NetworkCallback() {

                override fun onAvailable(network: Network) = emitter.onNext(true)

                override fun onLost(network: Network) = emitter.onNext(false)
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                manager.registerDefaultNetworkCallback(callback)
            } else {
                val builder = NetworkRequest.Builder()
                    .addTransportType(TRANSPORT_CELLULAR)
                    .addTransportType(TRANSPORT_WIFI)
                manager.registerNetworkCallback(builder.build(), callback)
            }
            emitter.onNext(manager.activeNetworkInfo?.isConnected ?: false)
            emitter.setCancellable { manager.unregisterNetworkCallback(callback) }

        }.distinctUntilChanged()
    }
}*/