package my.luckydog.presentation.fragments.signup.views

import android.content.Context

interface BrowserView {

    fun openUrl(context: Context, url: String): Boolean
}