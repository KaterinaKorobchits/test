package my.luckydog.presentation.dialogs.timepicker

import android.app.Dialog
import android.content.Context
import my.luckydog.presentation.dialogs.DialogParams
import my.luckydog.presentation.dialogs.factories.DialogCreator

class TimePickerDialogCreator : DialogCreator {

    override fun create(context: Context, params: DialogParams): Dialog {
        params as TimePickerDialogParams
        return MyTimePickerDialog.Builder(context)
            .theme(params.theme)
            .cancelable(params.isCancelable)
            .hour(params.hour)
            .minute(params.minute)
            .setTime(params.setTime)
            .is24Hour(params.is24Hour)
            .build()
    }
}