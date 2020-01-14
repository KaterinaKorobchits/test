package my.luckydog.presentation.dialogs.progress.determinate

import my.luckydog.presentation.R
import my.luckydog.presentation.dialogs.DialogParams

data class DeterminateDialogParams(
    val form: DeterminateDialogForm,
    override val isCancelable: Boolean = false,
    override val theme: Int = R.style.ProgressDialogTheme
) : DialogParams(isCancelable, theme)