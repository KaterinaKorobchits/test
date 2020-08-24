package my.luckydog.presentation.fragments.signup.views

import android.content.Context
import android.content.Intent
import android.net.Uri
import javax.inject.Inject

class BrowserViewImpl @Inject constructor() : BrowserView {

    init {
        println("!!!init: BrowserViewImpl - ${this::class.java.name} ${this.hashCode()}")
    }

    override fun openUrl(context: Context, url: String): Boolean {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        return intent.resolveActivity(context.packageManager)?.let {
            context.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
            true
        } ?: false
    }
}