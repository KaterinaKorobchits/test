package my.luckydog.presentation.fragments.notes.dialogs

import android.content.Context

interface NotesDialogs {

    fun prepare(context: Context)

    fun dismiss()

    fun enterWord(context: Context, positive: (String) -> Unit)

    fun showProgress(context: Context)

    fun hideProgress()

    fun showDetectLangError(context: Context)

    fun showUnknownError(context: Context)
}