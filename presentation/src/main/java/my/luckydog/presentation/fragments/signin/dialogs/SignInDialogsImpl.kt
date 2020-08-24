package my.luckydog.presentation.fragments.signin.dialogs

import android.content.Context
import android.content.res.Resources
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import my.luckydog.presentation.R
import my.luckydog.presentation.dialogs.DialogManager
import my.luckydog.presentation.dialogs.message.MessageDialogForm
import my.luckydog.presentation.dialogs.message.MessageDialogParams
import my.luckydog.presentation.dialogs.progress.indeterminate.IndeterminateDialogForm
import my.luckydog.presentation.dialogs.progress.indeterminate.IndeterminateDialogParams
import javax.inject.Inject
import kotlin.LazyThreadSafetyMode.NONE

class SignInDialogsImpl @Inject constructor(
    private val resources: Resources,
    private val manager: DialogManager
) : SignInDialogs {

    private companion object {
        private const val UNKNOWN_ERROR = "sign_in_unknown_error"
        private const val PROGRESS = "sign_in_progress"
        private const val CREDENTIALS_INCORRECT = "sign_in_credentials_incorrect"
    }

    init {
        println("!!!init: SignInDialogsImpl - ${this::class.java.name} ${this.hashCode()}")
    }

    private val progress: IndeterminateDialogParams by lazy(NONE) {
        IndeterminateDialogParams(IndeterminateDialogForm().apply {
            text.set(resources.getString(R.string.loading))
        })
    }

    private val unknown: MessageDialogParams by lazy(NONE) {
        val form = MessageDialogForm().apply {
            iconRes.set(R.drawable.ic_info_black_24dp)
            message.set(resources.getString(R.string.smth_went_wrong))
            neutral.set(resources.getString(R.string.ok))
        }
        MessageDialogParams(form)
    }

    private val incorrectCredentialsForm: MessageDialogForm by lazy(NONE) {
        MessageDialogForm().apply { neutral.set(resources.getString(R.string.ok)) }
    }

    override fun showProgress(context: Context) = manager.show(context, PROGRESS, progress)

    override fun hideProgress() = manager.dismiss(PROGRESS)

    override fun showUnknownError(context: Context) = manager.show(context, UNKNOWN_ERROR, unknown)

    override fun showCredentialsIncorrect(context: Context, message: String) {
        incorrectCredentialsForm.apply {
            iconDrawable.set(
                AnimatedVectorDrawableCompat.create(
                    context.applicationContext,
                    R.drawable.ic_warning_blinking_anim
                )
            )
            this.message.set(message)
        }

        manager.show(context, CREDENTIALS_INCORRECT, MessageDialogParams(incorrectCredentialsForm))
    }

    override fun prepare(context: Context) {
        manager.clearExcept(listOf(UNKNOWN_ERROR, PROGRESS, CREDENTIALS_INCORRECT))
        manager.showNext(context)
    }

    override fun dismiss() = manager.dismiss()
}