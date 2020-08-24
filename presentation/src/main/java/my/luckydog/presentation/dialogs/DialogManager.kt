package my.luckydog.presentation.dialogs

import android.content.Context

interface DialogManager {

    fun show(context: Context, id: String, params: DialogParams)

    fun dismiss(id: String, showNext: Boolean = true)

    fun dismiss()

    fun clearExcept(dialogsId: List<String>)

    fun showNext(context: Context)
}