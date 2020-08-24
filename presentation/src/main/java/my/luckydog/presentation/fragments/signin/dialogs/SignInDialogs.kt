package my.luckydog.presentation.fragments.signin.dialogs

import android.content.Context

interface SignInDialogs {

    fun prepare(context: Context)

    fun dismiss()

    fun showProgress(context: Context)

    fun hideProgress()

    fun showUnknownError(context: Context)

    fun showCredentialsIncorrect(context: Context, message: String)
}