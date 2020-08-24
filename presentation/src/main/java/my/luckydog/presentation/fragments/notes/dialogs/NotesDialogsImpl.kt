package my.luckydog.presentation.fragments.notes.dialogs

import android.content.Context
import android.content.res.Resources
import my.luckydog.presentation.R
import my.luckydog.presentation.dialogs.DialogManager
import my.luckydog.presentation.dialogs.input.InputDialogForm
import my.luckydog.presentation.dialogs.input.InputDialogParams
import my.luckydog.presentation.dialogs.message.MessageDialogForm
import my.luckydog.presentation.dialogs.message.MessageDialogParams
import my.luckydog.presentation.dialogs.progress.indeterminate.IndeterminateDialogForm
import my.luckydog.presentation.dialogs.progress.indeterminate.IndeterminateDialogParams
import javax.inject.Inject
import kotlin.LazyThreadSafetyMode.NONE

class NotesDialogsImpl @Inject constructor(
    private val resources: Resources,
    private val manager: DialogManager
) : NotesDialogs {

    private companion object {
        private const val DETECT_LANG_ERROR = "notes_detect_lang_error"
        private const val UNKNOWN_ERROR = "notes_unknown_error"
        private const val PROGRESS = "notes_progress"
        private const val ENTER = "notes_enter_word"
    }

    init {
        println("!!!init: ${this::class.java.name} ${this.hashCode()}")
    }

    private val progress: IndeterminateDialogParams by lazy(NONE) {
        IndeterminateDialogParams(IndeterminateDialogForm().apply {
            text.set(resources.getString(R.string.loading))
        })
    }

    private val detectLang: MessageDialogParams by lazy(NONE) {
        val form = MessageDialogForm().apply {
            iconRes.set(R.drawable.ic_info_black_24dp)
            message.set("Detect Language Error")
            neutral.set(resources.getString(R.string.ok))
        }
        MessageDialogParams(form)
    }

    private val unknown: MessageDialogParams by lazy(NONE) {
        val form = MessageDialogForm().apply {
            iconRes.set(R.drawable.ic_info_black_24dp)
            message.set(resources.getString(R.string.smth_went_wrong))
            neutral.set(resources.getString(R.string.ok))
        }
        MessageDialogParams(form)
    }

    private val enterForm: InputDialogForm by lazy(NONE) {
        InputDialogForm().apply {
            message.set("Enter word")
            negative.set("Cancel")
            positive.set("Add")
        }
    }

    override fun prepare(context: Context) {
        manager.clearExcept(listOf(UNKNOWN_ERROR, PROGRESS, ENTER))
        manager.showNext(context)
    }

    override fun dismiss() = manager.dismiss()

    override fun enterWord(context: Context, positive: (String) -> Unit) {
        enterForm.apply { clearInput() }
        val params = InputDialogParams(enterForm, positive = positive)
        manager.show(context, ENTER, params)
    }

    override fun showProgress(context: Context) = manager.show(context, PROGRESS, progress)

    override fun hideProgress() = manager.dismiss(PROGRESS)

    override fun showDetectLangError(context: Context) {
        manager.show(context, DETECT_LANG_ERROR, detectLang)
    }

    override fun showUnknownError(context: Context) = manager.show(context, UNKNOWN_ERROR, unknown)
}