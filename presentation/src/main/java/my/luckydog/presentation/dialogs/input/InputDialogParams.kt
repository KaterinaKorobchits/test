package my.luckydog.presentation.dialogs.input

import my.luckydog.presentation.R
import my.luckydog.presentation.dialogs.DialogParams

data class InputDialogParams(
    val form: InputDialogForm,
    val isAutoDismissNegative: Boolean = true,
    val isAutoDismissPositive: Boolean = true,
    val isAutoDismissNeutral: Boolean = true,
    val positive: (input: String) -> Unit = {},
    val negative: () -> Unit = {},
    val neutral: () -> Unit = {},
    override val isCancelable: Boolean = false,
    override val theme: Int = R.style.InputDialogTheme
) : DialogParams(isCancelable, theme)