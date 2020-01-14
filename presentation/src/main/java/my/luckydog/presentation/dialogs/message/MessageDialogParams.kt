package my.luckydog.presentation.dialogs.message

import my.luckydog.presentation.R
import my.luckydog.presentation.dialogs.DialogParams
import my.luckydog.presentation.dialogs.buttonbar.ButtonDialogForm

data class MessageDialogParams(
    val form: MessageDialogForm,
    val buttonForm: ButtonDialogForm,
    val isAutoDismissNegative: Boolean = true,
    val isAutoDismissPositive: Boolean = true,
    val isAutoDismissNeutral: Boolean = true,
    val positive: () -> Unit = {},
    val negative: () -> Unit = {},
    val neutral: () -> Unit = {},
    override val isCancelable: Boolean = false,
    override val theme: Int = R.style.DateTimePickerDialogTheme
) : DialogParams(isCancelable, theme)