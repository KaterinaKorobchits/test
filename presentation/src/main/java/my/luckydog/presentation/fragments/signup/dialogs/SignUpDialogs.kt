package my.luckydog.presentation.fragments.signup.dialogs

import android.content.Context

interface SignUpDialogs {

    fun prepare(context: Context)

    fun dismiss()

    fun showProgress(context: Context)

    fun hideProgress()

    fun showUnknownError(context: Context)
}