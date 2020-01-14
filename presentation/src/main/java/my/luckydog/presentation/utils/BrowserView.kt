package my.luckydog.presentation.utils

import android.content.Context

interface BrowserView {

    fun openUrl(context: Context, url: String): Boolean
}