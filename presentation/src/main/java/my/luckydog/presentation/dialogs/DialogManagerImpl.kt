package my.luckydog.presentation.dialogs

import android.app.Dialog
import android.content.Context
import my.luckydog.presentation.core.extensions.activityContext
import my.luckydog.presentation.dialogs.factories.DialogFactory
import java.lang.ref.WeakReference

class DialogManagerImpl(
    private val factory: DialogFactory,
    private val buffer: DialogsBuffer
) : DialogManager {

    init {
        println("!!!init: DialogManagerImpl - ${this::class.java.name} ${this.hashCode()}")
    }

    private var showedDialog = WeakReference<Dialog>(null)

    override fun show(context: Context, id: String, params: DialogParams) {
        if (!buffer.isContain(id)) buffer.add(id, params)
        if (!isDialogShowed()) prepareDialog(context, id, params).show()
    }

    override fun dismiss(id: String, showNext: Boolean) {
        if (!buffer.isContain(id)) return

        if (showedDialog.get() != null && buffer.getFirstId() == id) {
            showedDialog.get()?.run {
                setOnDismissListener(null)
                dismiss()
                buffer.remove(id)
                if (showNext) showNext(activityContext())
            }
            if (!showNext) showedDialog.clear()
        } else buffer.remove(id)
    }

    override fun dismiss() {
        if (isDialogShowed()) showedDialog.get()?.run {
            setOnDismissListener(null)
            dismiss()
        }
    }

    override fun clearExcept(dialogsId: List<String>) = buffer.getIds()
        .filter { !dialogsId.contains(it) }
        .forEach { dismiss(it) }

    override fun showNext(context: Context) {
        if (buffer.isNotEmpty()) show(context, buffer.getFirstId(), buffer.getFirst())
    }

    private fun isDialogShowed(): Boolean = showedDialog.get()?.isShowing ?: false

    private fun prepareDialog(context: Context, id: String, params: DialogParams): Dialog {
        return factory.create(context, params).apply {
            setOnDismissListener { dismiss(id) }
            showedDialog = WeakReference(this)
        }
    }
}