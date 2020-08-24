package my.luckydog.presentation.dialogs.message

import android.app.Dialog
import android.content.Context
import my.luckydog.presentation.dialogs.DialogParams
import my.luckydog.presentation.dialogs.factories.DialogCreator
import javax.inject.Inject

class MessageDialogCreator @Inject constructor() : DialogCreator {

    init {
        println("!!!init: MessageDialogCreator - ${this::class.java.name} ${this.hashCode()}")
    }

    override fun create(context: Context, params: DialogParams): Dialog {
        params as MessageDialogParams
        return MessageDialog.Builder(context)
            .theme(params.theme)
            .cancelable(params.isCancelable)
            .form(params.form)
            .autoDismissNegative(params.isAutoDismissNegative)
            .autoDismissPositive(params.isAutoDismissPositive)
            .autoDismissNeutral(params.isAutoDismissNeutral)
            .setPositive(params.positive)
            .setNeutral(params.neutral)
            .setNegative(params.negative)
            .build()
    }
}