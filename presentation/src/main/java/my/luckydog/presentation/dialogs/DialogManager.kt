package my.luckydog.presentation.dialogs

import android.content.Context

interface DialogManager {

    fun show(context: Context, id: String, params: DialogParams)

    fun dismiss(id: String, showNext: Boolean = true)

    fun dismiss()

    fun clear()

    fun clearDialogs(dialogsId: List<String>)
}