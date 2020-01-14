package my.luckydog.presentation.fragments.signup.dialogs

import android.content.Context
import android.content.res.Resources
import my.luckydog.presentation.R
import my.luckydog.presentation.dialogs.DialogManager
import my.luckydog.presentation.dialogs.buttonbar.ButtonDialogForm
import my.luckydog.presentation.dialogs.message.MessageDialogForm
import my.luckydog.presentation.dialogs.message.MessageDialogParams
import my.luckydog.presentation.dialogs.progress.indeterminate.IndeterminateDialogForm
import my.luckydog.presentation.dialogs.progress.indeterminate.IndeterminateDialogParams

class SignUpDialogsImpl(
    private val manager: DialogManager,
    private val resources: Resources
) : SignUpDialogs {

    companion object {
        const val UNKNOWN_ERROR = "sign_up_unknown_error"
        const val PROGRESS = "sign_up_progress"
    }

    private val progressForm = IndeterminateDialogForm()

    override fun showProgress(context: Context, text: String) {
        progressForm.text.set(text)
        manager.show(context, PROGRESS, IndeterminateDialogParams(progressForm))
    }

    override fun hideProgress() {
        manager.dismiss(PROGRESS, false)
    }

    override fun showUnknownError(context: Context) {
        val form = MessageDialogForm().apply {
            iconRes.set(R.drawable.ic_info_black_24dp)
            message.set(resources.getString(R.string.smth_went_wrong))
        }
        val buttonForm = ButtonDialogForm().apply {
            neutral.set(resources.getString(R.string.ok))
        }
        manager.show(context, UNKNOWN_ERROR, MessageDialogParams(form, buttonForm))
    }

    override fun hide() {
        manager.dismiss()
    }

    override fun clear() {
        manager.clearDialogs(listOf(UNKNOWN_ERROR, PROGRESS))
    }
}