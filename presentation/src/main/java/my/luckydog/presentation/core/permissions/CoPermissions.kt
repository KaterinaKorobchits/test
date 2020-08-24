package my.luckydog.presentation.core.permissions

import androidx.fragment.app.FragmentManager
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import kotlin.coroutines.suspendCoroutine

class CoPermissions private constructor(private val fragment: CoPermissionsFragment) {

    companion object {
        private val TAG = CoPermissions::class.java.simpleName

        fun getInstance(manager: FragmentManager): CoPermissions {
            var fragment = manager.findFragmentByTag(TAG) as CoPermissionsFragment?
            if (fragment == null) {
                fragment = CoPermissionsFragment()
                manager
                    .beginTransaction()
                    .add(fragment, TAG)
                    .commitNow()
            }
            return CoPermissions(fragment)
        }
    }

    /*fun request(permission: String): Flow<Permission> = callbackFlow {

    }*/

    fun request(vararg permissions: String): Flow<Permission> = callbackFlow {
        val unrequestedPermissions = ArrayList<String>()

        for (permission: String in permissions) {
            if (isGranted(permission)) {
                offer(Permission.Granted(permission))
                continue
            }

            if (isRevoked(permission)) {
                offer(Permission.RevokedByPolicy(permission))
                continue
            }

            var channel = fragment.getSubjectByPermission(permission)
            if (channel == null) {
                unrequestedPermissions.add(permission)
                channel = BroadcastChannel<Permission>(1)
                fragment.setSubjectForPermission(permission, channel)
            }
            //channel.asFlow().collect { offer(it) }
        }

        if (unrequestedPermissions.isNotEmpty()) {
            fragment.requestPermissions(unrequestedPermissions.toString())
        }

        awaitClose {  }
    }

    private fun isGranted(permission: String): Boolean = fragment.isGranted(permission)

    private fun isRevoked(permission: String): Boolean = fragment.isRevoked(permission)

}