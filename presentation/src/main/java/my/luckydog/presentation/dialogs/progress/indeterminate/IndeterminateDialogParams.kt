package my.luckydog.presentation.dialogs.progress.indeterminate

import my.luckydog.presentation.R
import my.luckydog.presentation.dialogs.DialogParams

data class IndeterminateDialogParams(
    val form: IndeterminateDialogForm,
    override val isCancelable: Boolean = false,
    override val theme: Int = R.style.ProgressDialogTheme
) : DialogParams(isCancelable, theme)