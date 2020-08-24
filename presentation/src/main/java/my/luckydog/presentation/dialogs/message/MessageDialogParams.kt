package my.luckydog.presentation.dialogs.message

import my.luckydog.presentation.R
import my.luckydog.presentation.dialogs.DialogParams

data class MessageDialogParams(
    val form: MessageDialogForm,
    val isAutoDismissNegative: Boolean = true,
    val isAutoDismissPositive: Boolean = true,
    val isAutoDismissNeutral: Boolean = true,
    val positive: () -> Unit = {},
    val negative: () -> Unit = {},
    val neutral: () -> Unit = {},
    override val isCancelable: Boolean = false,
    override val theme: Int = R.style.MessageDialogTheme
) : DialogParams(isCancelable, theme)