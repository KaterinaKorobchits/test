package my.luckydog.presentation.dialogs.progress.determinate

import android.app.Dialog
import android.content.Context
import my.luckydog.presentation.dialogs.DialogParams
import my.luckydog.presentation.dialogs.factories.DialogCreator
import javax.inject.Inject

class DeterminateDialogCreator @Inject constructor() : DialogCreator {

    init {
        println("!!!init: DeterminateDialogCreator - ${this::class.java.name} ${this.hashCode()}")
    }

    override fun create(context: Context, params: DialogParams): Dialog {
        params as DeterminateDialogParams
        return DeterminateDialog.Builder(context)
            .theme(params.theme)
            .cancelable(params.isCancelable)
            .form(params.form)
            .build()
    }
}