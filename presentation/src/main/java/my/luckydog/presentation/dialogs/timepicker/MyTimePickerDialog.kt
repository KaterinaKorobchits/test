package my.luckydog.presentation.dialogs.timepicker

import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.Context
import android.os.Bundle
import my.luckydog.presentation.R

class MyTimePickerDialog private constructor(private val builder: Builder) : TimePickerDialog(
    builder.context, builder.theme,
    OnTimeSetListener { _, hour, minute -> builder.setTimeCallback.invoke(hour, minute) },
    builder.hour, builder.minute, builder.is24Hour
) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setCancelable(builder.isCancelable)
    }

    class Builder(internal val context: Context) {

        internal var hour: Int = 0
        internal var minute: Int = 0
        internal lateinit var setTimeCallback: (hour: Int, minute: Int) -> Unit
        internal var is24Hour: Boolean = true
        internal var isCancelable: Boolean = false
        internal var theme: Int = R.style.DateTimePickerDialogTheme

        fun hour(hour: Int) = apply { this.hour = hour }

        fun minute(minute: Int) = apply { this.minute = minute }

        fun setTime(setTime: (hour: Int, minute: Int) -> Unit) = apply { setTimeCallback = setTime }

        fun is24Hour(is24Hour: Boolean) = apply { this.is24Hour = is24Hour }

        fun cancelable(isCancelable: Boolean) = apply { this.isCancelable = isCancelable }

        fun theme(theme: Int) = apply { this.theme = theme }

        internal fun build(): MyTimePickerDialog = MyTimePickerDialog(this)
    }
}