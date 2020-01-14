package my.luckydog.presentation.dialogs.factories

import android.app.Dialog
import android.content.Context
import my.luckydog.presentation.dialogs.DialogParams
import my.luckydog.presentation.dialogs.datepicker.DatePickerDialogParams
import my.luckydog.presentation.dialogs.message.MessageDialogParams
import my.luckydog.presentation.dialogs.progress.determinate.DeterminateDialogParams
import my.luckydog.presentation.dialogs.progress.indeterminate.IndeterminateDialogParams
import my.luckydog.presentation.dialogs.timepicker.TimePickerDialogParams

class DialogFactoryImpl(
    private val message: DialogCreator,
    private val indeterminate: DialogCreator,
    private val determinate: DialogCreator,
    private val datePicker: DialogCreator,
    private val timePicker: DialogCreator
) : DialogFactory {

    override fun create(context: Context, params: DialogParams): Dialog = when (params) {
        is DatePickerDialogParams -> datePicker
        is TimePickerDialogParams -> timePicker
        is MessageDialogParams -> message
        is IndeterminateDialogParams -> indeterminate
        is DeterminateDialogParams -> determinate
        else -> throw Exception("not supported dialog: $params")
    }.create(context, params)
}