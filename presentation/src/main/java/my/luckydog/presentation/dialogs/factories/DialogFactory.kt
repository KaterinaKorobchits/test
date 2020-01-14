package my.luckydog.presentation.dialogs.factories

import android.app.Dialog
import android.content.Context
import my.luckydog.presentation.dialogs.DialogParams

interface DialogFactory {

    fun create(context: Context, params: DialogParams): Dialog
}