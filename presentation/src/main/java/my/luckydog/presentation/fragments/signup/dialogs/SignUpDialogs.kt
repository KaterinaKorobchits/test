package my.luckydog.presentation.fragments.signup.dialogs

import android.content.Context

interface SignUpDialogs {

    fun showProgress(context: Context, text: String)

    fun hideProgress()

    fun showUnknownError(context: Context)

    fun hide()

    fun clear()
}