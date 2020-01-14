package my.luckydog.presentation.dialogs.datepicker

import my.luckydog.presentation.R
import my.luckydog.presentation.dialogs.DialogParams

data class DatePickerDialogParams(
    val year: Int,
    val month: Int,
    val day: Int,
    val setDate: (year: Int, month: Int, day: Int) -> Unit = { _: Int, _: Int, _: Int -> },
    val minDate: Long = Long.MIN_VALUE,
    val maxDate: Long = Long.MIN_VALUE,
    override val isCancelable: Boolean = false,
    override val theme: Int = R.style.DateTimePickerDialogTheme
) : DialogParams(isCancelable, theme)