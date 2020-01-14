package my.luckydog.di.activity

import dagger.Module
import dagger.Provides
import my.luckydog.presentation.dialogs.DialogManager
import my.luckydog.presentation.dialogs.DialogManagerImpl
import my.luckydog.presentation.dialogs.DialogsBuffer
import my.luckydog.presentation.dialogs.DialogsBufferImpl
import my.luckydog.presentation.dialogs.datepicker.DatePickerDialogCreator
import my.luckydog.presentation.dialogs.factories.DialogCreator
import my.luckydog.presentation.dialogs.factories.DialogFactory
import my.luckydog.presentation.dialogs.factories.DialogFactoryImpl
import my.luckydog.presentation.dialogs.message.MessageDialogCreator
import my.luckydog.presentation.dialogs.progress.determinate.DeterminateDialogCreator
import my.luckydog.presentation.dialogs.progress.indeterminate.IndeterminateDialogCreator
import my.luckydog.presentation.dialogs.timepicker.TimePickerDialogCreator
import javax.inject.Named

@Module
class DialogsModule {

   private companion object {
        private const val MESSAGE_DIALOG = "message_dialog"
        private const val INDETERMINATE_DIALOG = "indeterminate_dialog"
        private const val DETERMINATE_DIALOG = "determinate_dialog"
        private const val DATE_PICKER_DIALOG = "date_picker_dialog"
        private const val TIME_PICKER_DIALOG = "time_picker_dialog"
    }

    @Provides
    @ActivityScope
    fun provideDialogManager(
        factory: DialogFactory,
        buffer: DialogsBuffer
    ): DialogManager {
        return DialogManagerImpl(factory, buffer)
    }

    @Provides
    @ActivityScope
    fun provideDialogsBuffer(): DialogsBuffer = DialogsBufferImpl()

    @Provides
    @ActivityScope
    fun provideDialogFactory(
        @Named(MESSAGE_DIALOG)
        message: DialogCreator,
        @Named(INDETERMINATE_DIALOG)
        indeterminate: DialogCreator,
        @Named(DETERMINATE_DIALOG)
        determinate: DialogCreator,
        @Named(DATE_PICKER_DIALOG)
        datePicker: DialogCreator,
        @Named(TIME_PICKER_DIALOG)
        timePicker: DialogCreator
    ): DialogFactory {
        return DialogFactoryImpl(message, indeterminate, determinate, datePicker, timePicker)
    }

    @Provides
    @ActivityScope
    @Named(MESSAGE_DIALOG)
    fun provideMessageDialog(): DialogCreator = MessageDialogCreator()

    @Provides
    @ActivityScope
    @Named(INDETERMINATE_DIALOG)
    fun provideIndeterminateDialog(): DialogCreator = IndeterminateDialogCreator()

    @Provides
    @ActivityScope
    @Named(DETERMINATE_DIALOG)
    fun provideDeterminateDialog(): DialogCreator = DeterminateDialogCreator()

    @Provides
    @ActivityScope
    @Named(DATE_PICKER_DIALOG)
    fun provideDatePickerDialog(): DialogCreator = DatePickerDialogCreator()

    @Provides
    @ActivityScope
    @Named(TIME_PICKER_DIALOG)
    fun provideTimePickerDialog(): DialogCreator = TimePickerDialogCreator()
}