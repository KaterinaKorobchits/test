package my.luckydog.presentation.fragments.signup.views

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import javax.inject.Inject

class AppSettingsViewImpl @Inject constructor() : AppSettingsView {

    init {
        println("!!!init: AppSettingsViewImpl - ${this::class.java.name} ${this.hashCode()}")
    }

    override fun open(context: Context) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = Uri.parse("package:" + context.packageName)
        context.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
    }
}