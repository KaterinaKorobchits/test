package my.luckydog.presentation.dialogs

import android.app.Dialog
import android.content.Context
import my.luckydog.presentation.dialogs.factories.DialogFactory
import my.luckydog.presentation.extensions.activityContext
import java.lang.ref.WeakReference

class DialogManagerImpl(
    private val factory: DialogFactory,
    private val buffer: DialogsBuffer
) : DialogManager {

    private var showedDialog = WeakReference<Dialog>(null)

    override fun show(context: Context, id: String, params: DialogParams) {
        if (buffer.isContain(id)) return

        buffer.add(id, params)
        if (!isDialogShowed()) prepareDialog(context, id, params).show()
    }

    override fun dismiss(id: String, showNext: Boolean) {
        if (!buffer.isContain(id)) return

        if (isDialogShowed(id)) {
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
        if (isDialogShowed()) dismiss(buffer.getFirstId(), false)
    }



    override fun clear() {
        if (isDialogShowed()) dismiss(buffer.getFirstId())
        buffer.clear()
    }

    override fun clearDialogs(dialogsId: List<String>) = dialogsId.forEach { buffer.remove(it) }

    private fun isDialogShowed(): Boolean = showedDialog.get()?.isShowing ?: false

    private fun prepareDialog(context: Context, id: String, params: DialogParams): Dialog {
        return factory.create(context, params).apply {
            setOnDismissListener { if (buffer.isNotEmpty()) dismiss(id) }
            showedDialog = WeakReference(this)
        }
    }

    private fun isDialogShowed(id: String): Boolean = isDialogShowed() && buffer.getFirstId() == id

    private fun showNext(context: Context) {
        if (buffer.isNotEmpty()) show(context, buffer.getFirstId(), buffer.getFirst())
    }
}