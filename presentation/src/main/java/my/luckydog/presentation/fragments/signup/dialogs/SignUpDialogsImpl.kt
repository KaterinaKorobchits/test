package my.luckydog.presentation.fragments.signup.dialogs

import android.content.Context
import android.content.res.Resources
import my.luckydog.presentation.R
import my.luckydog.presentation.dialogs.DialogManager
import my.luckydog.presentation.dialogs.message.MessageDialogForm
import my.luckydog.presentation.dialogs.message.MessageDialogParams
import my.luckydog.presentation.dialogs.progress.indeterminate.IndeterminateDialogForm
import my.luckydog.presentation.dialogs.progress.indeterminate.IndeterminateDialogParams
import javax.inject.Inject
import kotlin.LazyThreadSafetyMode.NONE

class SignUpDialogsImpl @Inject constructor(
    private val resources: Resources,
    private val manager: DialogManager
) : SignUpDialogs {

    private companion object {
        private const val UNKNOWN_ERROR = "sign_up_unknown_error"
        private const val PROGRESS = "sign_up_progress"
    }

    init {
        println("!!!init: ${this::class.java.name} ${this.hashCode()}")
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

    override fun showProgress(context: Context) = manager.show(context, PROGRESS, progress)

    override fun hideProgress() = manager.dismiss(PROGRESS, false)

    override fun showUnknownError(context: Context) = manager.show(context, UNKNOWN_ERROR, unknown)

    override fun prepare(context: Context) {
        manager.clearExcept(listOf(UNKNOWN_ERROR, PROGRESS))
        manager.showNext(context)
    }

    override fun dismiss() = manager.dismiss()
}