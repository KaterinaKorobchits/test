package my.luckydog.presentation.core.permissions

import android.annotation.TargetApi
import android.content.pm.PackageManager
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel

class CoPermissionsFragment : Fragment() {

    private companion object {
        private const val REQUEST_CODE = 42
        private val TAG = CoPermissionsFragment::class.java.simpleName
    }

    private val channels = HashMap<String, BroadcastChannel<Permission>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    fun requestPermissions(vararg permissions: String) {
        println("requestPermissions")
        requestPermissions(permissions, REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_CODE) {
            val shouldShow = BooleanArray(permissions.size)

            permissions.forEachIndexed { i, s ->
                shouldShow[i] =
                    shouldShowRequestPermissionRationale(permissions[i])
            }

            permissionsResult(permissions, grantResults, shouldShow)
        }
    }

    private fun permissionsResult(
        permissions: Array<out String>,
        grantResults: IntArray,
        shouldShow: BooleanArray
    ) {
        println("permissionsResult")
        permissions.forEachIndexed { i, s ->

            println("permissionsResult - $s / ${grantResults[i]}")

            val channel = channels[s]

            channels.remove(s)
            val granted = grantResults[i] == PERMISSION_GRANTED

            channel?.offer(if (granted) Permission.Granted(s) else Permission.Denied(s))
        }
    }

    fun getSubjectByPermission(permission: String): BroadcastChannel<Permission>? =
        channels[permission]

    fun containsByPermission(permission: String): Boolean = channels.containsKey(permission)

    fun setSubjectForPermission(permission: String, channel: BroadcastChannel<Permission>) {
        channels[permission] = channel
    }

    fun isGranted(permission: String): Boolean {
        return activity?.run { checkSelfPermission(permission) == PERMISSION_GRANTED }
            ?: throw IllegalStateException("This fragment $TAG must be attached to an activity.")
    }

    fun isRevoked(permission: String): Boolean {
        return activity?.run {
            packageManager?.isPermissionRevokedByPolicy(permission, packageName)
        } ?: throw IllegalStateException("This fragment $TAG must be attached to an activity.")
    }
}