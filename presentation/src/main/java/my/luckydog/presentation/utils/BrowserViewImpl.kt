package my.luckydog.presentation.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

class BrowserViewImpl : BrowserView {

    override fun openUrl(context: Context, url: String): Boolean {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        return intent.resolveActivity(context.packageManager)?.let {
            context.startActivity(intent)
            true
        } ?: false
    }
}