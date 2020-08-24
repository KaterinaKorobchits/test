package my.luckydog.presentation.dialogs.datepicker

import android.app.Dialog
import android.content.Context
import my.luckydog.presentation.dialogs.DialogParams
import my.luckydog.presentation.dialogs.factories.DialogCreator
import javax.inject.Inject

class DatePickerDialogCreator @Inject constructor() : DialogCreator {

    init {
        println("!!!init: DatePickerDialogCreator - ${this::class.java.name} ${this.hashCode()}")
    }

    override fun create(context: Context, params: DialogParams): Dialog {
        params as DatePickerDialogParams
        return PickerDateDialog.Builder(context)
            .theme(params.theme)
            .cancelable(params.isCancelable)
            .year(params.year)
            .month(params.month)
            .day(params.day)
            .setDate(params.setDate)
            .minDate(params.minDate)
            .maxDate(params.maxDate)
            .build()
    }
}