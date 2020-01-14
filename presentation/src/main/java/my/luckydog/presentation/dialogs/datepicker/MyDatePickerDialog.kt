package my.luckydog.presentation.dialogs.datepicker

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import android.os.Bundle
import my.luckydog.presentation.R

class MyDatePickerDialog private constructor(private val builder: Builder) : DatePickerDialog(
    builder.context, builder.theme,
    OnDateSetListener { _, year, month, dayOfMonth ->
        builder.setDateCallback.invoke(year, month, dayOfMonth)
    }, builder.year, builder.month, builder.day
) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setCancelable(builder.isCancelable)
        if (builder.minDate != Long.MIN_VALUE) datePicker.minDate = builder.minDate
        if (builder.maxDate != Long.MIN_VALUE) datePicker.maxDate = builder.maxDate
    }

    class Builder(internal val context: Context) {

        internal var year: Int = 0
        internal var month: Int = 0
        internal var day: Int = 0
        internal lateinit var setDateCallback: (year: Int, month: Int, day: Int) -> Unit
        internal var minDate: Long = Long.MIN_VALUE
        internal var maxDate: Long = Long.MIN_VALUE
        internal var isCancelable: Boolean = false
        internal var theme: Int = R.style.DateTimePickerDialogTheme

        fun year(year: Int) = apply { this.year = year }

        fun month(month: Int) = apply { this.month = month }

        fun day(day: Int) = apply { this.day = day }

        fun setDate(setDate: (year: Int, month: Int, day: Int) -> Unit) = apply {
            setDateCallback = setDate
        }

        fun minDate(minDate: Long) = apply { this.minDate = minDate }

        fun maxDate(maxDate: Long) = apply { this.maxDate = maxDate }

        fun cancelable(isCancelable: Boolean) = apply { this.isCancelable = isCancelable }

        fun theme(theme: Int) = apply { this.theme = theme }

        internal fun build(): MyDatePickerDialog = MyDatePickerDialog(this)
    }
}