package my.luckydog.presentation.dialogs.input

import android.app.Dialog
import android.content.Context
import my.luckydog.presentation.dialogs.DialogParams
import my.luckydog.presentation.dialogs.factories.DialogCreator
import javax.inject.Inject

class InputDialogCreator @Inject constructor() : DialogCreator {

    init {
        println("!!!init: InputDialogCreator - ${this::class.java.name} ${this.hashCode()}")
    }

    override fun create(context: Context, params: DialogParams): Dialog {
        params as InputDialogParams
        return InputDialog.Builder(context)
            .theme(params.theme)
            .cancelable(params.isCancelable)
            .form(params.form)
            .autoDismissNegative(params.isAutoDismissNegative)
            .autoDismissPositive(params.isAutoDismissPositive)
            .autoDismissNeutral(params.isAutoDismissNeutral)
            .setPositive(params.positive)
            .setNegative(params.negative)
            .setNeutral(params.neutral)
            .build()
    }
}