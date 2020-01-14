package my.luckydog.presentation.dialogs.message

import android.app.Dialog
import android.content.Context
import my.luckydog.presentation.dialogs.DialogParams
import my.luckydog.presentation.dialogs.factories.DialogCreator

class MessageDialogCreator : DialogCreator {

    override fun create(context: Context, params: DialogParams): Dialog {
        params as MessageDialogParams
        return MessageDialog.Builder(context)
            .theme(params.theme)
            .cancelable(params.isCancelable)
            .form(params.form)
            .buttonForm(params.buttonForm)
            .autoDismissNegative(params.isAutoDismissNegative)
            .autoDismissPositive(params.isAutoDismissPositive)
            .autoDismissNeutral(params.isAutoDismissNeutral)
            .setPositive(params.positive)
            .setNeutral(params.neutral)
            .setNegative(params.negative)
            .build()
    }
}