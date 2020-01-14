package my.luckydog.presentation.fragments.signin.dialogs

import android.content.Context
import android.content.res.Resources
import my.luckydog.presentation.R
import my.luckydog.presentation.dialogs.DialogManager
import my.luckydog.presentation.dialogs.buttonbar.ButtonDialogForm
import my.luckydog.presentation.dialogs.message.MessageDialogForm
import my.luckydog.presentation.dialogs.message.MessageDialogParams
import my.luckydog.presentation.dialogs.progress.indeterminate.IndeterminateDialogForm
import my.luckydog.presentation.dialogs.progress.indeterminate.IndeterminateDialogParams

class SignInDialogsImpl(
    private val manager: DialogManager,
    private val resources: Resources
) : SignInDialogs {

    companion object {
        const val UNKNOWN_ERROR = "sign_in_unknown_error"
        const val PROGRESS = "sign_in_progress"
        const val CREDENTIALS_INCORRECT = "sign_in_credentials_incorrect"
    }

    private val progressForm = IndeterminateDialogForm()

    override fun showProgress(context: Context, text: String) {
        progressForm.text.set(text)
        manager.show(context, PROGRESS, IndeterminateDialogParams(progressForm))
    }

    override fun hideProgress() {
        manager.dismiss(PROGRESS)
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

    override fun showCredentialsIncorrect(context: Context, message: String) {
        val form = MessageDialogForm().apply {
            iconRes.set(R.drawable.ic_info_black_24dp)
            this.message.set(message)
        }
        val buttonForm = ButtonDialogForm().apply {
            neutral.set(resources.getString(R.string.ok))
        }
        manager.show(context, CREDENTIALS_INCORRECT, MessageDialogParams(form, buttonForm))
    }

    override fun hide() {
        manager.dismiss()
    }

    override fun clear() {
        manager.clearDialogs(listOf(UNKNOWN_ERROR, PROGRESS, CREDENTIALS_INCORRECT))
    }
}