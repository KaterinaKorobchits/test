package my.luckydog.presentation.dialogs.progress.indeterminate

import android.app.Dialog
import android.content.Context
import my.luckydog.presentation.dialogs.DialogParams
import my.luckydog.presentation.dialogs.factories.DialogCreator
import javax.inject.Inject

class IndeterminateDialogCreator @Inject constructor() : DialogCreator {

    init {
        println("!!!init: IndeterminateDialogCreator - ${this::class.java.name} ${this.hashCode()}")
    }

    override fun create(context: Context, params: DialogParams): Dialog {
        params as IndeterminateDialogParams
        return IndeterminateDialog.Builder(context)
            .theme(params.theme)
            .cancelable(params.isCancelable)
            .form(params.form)
            .build()
    }
}