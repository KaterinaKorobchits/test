package my.luckydog.di.activity

import dagger.Binds
import dagger.Module
import dagger.Provides
import my.luckydog.di.scopes.ActivityScope
import my.luckydog.presentation.dialogs.DialogManager
import my.luckydog.presentation.dialogs.DialogManagerImpl
import my.luckydog.presentation.dialogs.DialogsBuffer
import my.luckydog.presentation.dialogs.DialogsBufferImpl
import my.luckydog.presentation.dialogs.datepicker.DatePickerDialogCreator
import my.luckydog.presentation.dialogs.factories.DialogCreator
import my.luckydog.presentation.dialogs.factories.DialogFactory
import my.luckydog.presentation.dialogs.factories.DialogFactoryImpl
import my.luckydog.presentation.dialogs.input.InputDialogCreator
import my.luckydog.presentation.dialogs.message.MessageDialogCreator
import my.luckydog.presentation.dialogs.progress.determinate.DeterminateDialogCreator
import my.luckydog.presentation.dialogs.progress.indeterminate.IndeterminateDialogCreator
import my.luckydog.presentation.dialogs.timepicker.TimePickerDialogCreator
import javax.inject.Named

@Module
abstract class DialogsModule {

    @Module
    companion object {
        private const val MESSAGE_DIALOG = "message_dialog_creator"
        private const val INPUT_DIALOG = "input_dialog_creator"
        private const val INDETERMINATE_DIALOG = "indeterminate_dialog_creator"
        private const val DETERMINATE_DIALOG = "determinate_dialog_creator"
        private const val DATE_PICKER_DIALOG = "date_picker_dialog_creator"
        private const val TIME_PICKER_DIALOG = "time_picker_dialog_creator"

        @JvmStatic
        @Provides
        @ActivityScope
        fun provideDialogManager(
            factory: DialogFactory,
            buffer: DialogsBuffer
        ): DialogManager {
            println("!!!dagger: provideDialogManager")
            return DialogManagerImpl(factory, buffer)
        }

        @JvmStatic
        @Provides
        @ActivityScope
        fun provideDialogFactory(
            @Named(MESSAGE_DIALOG)
            message: DialogCreator,
            @Named(INPUT_DIALOG)
            input: DialogCreator,
            @Named(INDETERMINATE_DIALOG)
            indeterminate: DialogCreator,
            @Named(DETERMINATE_DIALOG)
            determinate: DialogCreator,
            @Named(DATE_PICKER_DIALOG)
            datePicker: DialogCreator,
            @Named(TIME_PICKER_DIALOG)
            timePicker: DialogCreator
        ): DialogFactory {
            println("!!!dagger: provideDialogFactory")
            return DialogFactoryImpl(message, input, indeterminate, determinate, datePicker, timePicker)
        }
    }

    @Binds
    @ActivityScope
    abstract fun provideDialogsBuffer(dialogBuffer: DialogsBufferImpl): DialogsBuffer

    @Binds
    @ActivityScope
    @Named(MESSAGE_DIALOG)
    abstract fun provideMessageDialog(creator: MessageDialogCreator): DialogCreator

    @Binds
    @ActivityScope
    @Named(INPUT_DIALOG)
    abstract fun provideInputDialog(creator: InputDialogCreator): DialogCreator

    @Binds
    @ActivityScope
    @Named(INDETERMINATE_DIALOG)
    abstract fun provideIndeterminateDialog(creator: IndeterminateDialogCreator): DialogCreator

    @Binds
    @ActivityScope
    @Named(DETERMINATE_DIALOG)
    abstract fun provideDeterminateDialog(creator: DeterminateDialogCreator): DialogCreator

    @Binds
    @ActivityScope
    @Named(DATE_PICKER_DIALOG)
    abstract fun provideDatePickerDialog(creator: DatePickerDialogCreator): DialogCreator

    @Binds
    @ActivityScope
    @Named(TIME_PICKER_DIALOG)
    abstract fun provideTimePickerDialog(creator: TimePickerDialogCreator): DialogCreator
}