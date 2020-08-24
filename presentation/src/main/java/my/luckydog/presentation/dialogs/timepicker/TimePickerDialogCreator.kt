package my.luckydog.presentation.dialogs.timepicker

import android.app.Dialog
import android.content.Context
import my.luckydog.presentation.dialogs.DialogParams
import my.luckydog.presentation.dialogs.factories.DialogCreator
import javax.inject.Inject

class TimePickerDialogCreator @Inject constructor() : DialogCreator {

    init {
        println("!!!init: TimePickerDialogCreator - ${this::class.java.name} ${this.hashCode()}")
    }

    override fun create(context: Context, params: DialogParams): Dialog {
        params as TimePickerDialogParams
        return PickerTimeDialog.Builder(context)
            .theme(params.theme)
            .cancelable(params.isCancelable)
            .hour(params.hour)
            .minute(params.minute)
            .setTime(params.setTime)
            .is24Hour(params.is24Hour)
            .build()
    }
}