package my.luckydog.presentation.fragments.signin.dialogs

import android.content.Context

interface SignInDialogs {

    fun showProgress(context: Context, text: String)

    fun hideProgress()

    fun showUnknownError(context: Context)

    fun showCredentialsIncorrect(context: Context, message: String)

    fun hide()

    fun clear()
}