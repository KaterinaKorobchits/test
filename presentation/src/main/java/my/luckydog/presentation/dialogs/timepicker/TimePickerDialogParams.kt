package my.luckydog.presentation.dialogs.timepicker

import my.luckydog.presentation.R
import my.luckydog.presentation.dialogs.DialogParams

data class TimePickerDialogParams(
    val hour: Int,
    val minute: Int,
    val setTime: (hour: Int, minute: Int) -> Unit = { _: Int, _: Int -> },
    val is24Hour: Boolean = true,
    override val isCancelable: Boolean = false,
    override val theme: Int = R.style.TimePickerDialogTheme
) : DialogParams(isCancelable, theme)