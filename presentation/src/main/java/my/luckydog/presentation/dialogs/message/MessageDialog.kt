package my.luckydog.presentation.dialogs.message

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import my.luckydog.presentation.R
import my.luckydog.presentation.databinding.DialogMessageBinding
import my.luckydog.presentation.dialogs.buttonbar.ButtonDialogForm
import my.luckydog.presentation.dialogs.buttonbar.ButtonDialogHandler
import my.luckydog.presentation.extensions.background
import my.luckydog.presentation.extensions.fullScreen
import my.luckydog.presentation.extensions.inflater

class MessageDialog : Dialog {

    private lateinit var builder: Builder

    private val positive: () -> Unit = {
        if (builder.isAutoDismissPositive) dismiss()
        builder.positiveCallback.invoke()
    }

    private val negative: () -> Unit = {
        if (builder.isAutoDismissNegative) dismiss()
        builder.negativeCallback.invoke()
    }

    private val neutral: () -> Unit = {
        if (builder.isAutoDismissNeutral) dismiss()
        builder.neutralCallback.invoke()
    }

    private constructor(context: Context, theme: Int) : super(context, theme)

    private constructor(builder: Builder) : this(builder.context, builder.theme) {
        this.builder = builder
        setCancelable(builder.isCancelable)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        val binding = DialogMessageBinding.inflate(context.inflater(builder.theme))
            .apply {
                form = builder.form
                buttonForm = builder.buttonForm
                handler = ButtonDialogHandler(positive, negative, neutral)
            }
        setContentView(binding.root)

        window?.run {
            fullScreen()
            background(android.R.color.transparent)
        }
    }

    class Builder(internal val context: Context) {

        internal lateinit var form: MessageDialogForm
        internal lateinit var buttonForm: ButtonDialogForm
        internal var isAutoDismissNegative: Boolean = true
        internal var isAutoDismissPositive: Boolean = true
        internal var isAutoDismissNeutral: Boolean = true
        internal lateinit var positiveCallback: () -> Unit
        internal lateinit var negativeCallback: () -> Unit
        internal lateinit var neutralCallback: () -> Unit
        internal var isCancelable: Boolean = false
        internal var theme: Int = R.style.DateTimePickerDialogTheme

        fun form(form: MessageDialogForm) = apply { this.form = form }

        fun buttonForm(form: ButtonDialogForm) = apply { buttonForm = form }

        fun autoDismissNegative(dismiss: Boolean) = apply { isAutoDismissNegative = dismiss }

        fun autoDismissPositive(dismiss: Boolean) = apply { isAutoDismissPositive = dismiss }

        fun autoDismissNeutral(dismiss: Boolean) = apply { isAutoDismissNeutral = dismiss }

        fun setPositive(setPositive: () -> Unit) = apply { positiveCallback = setPositive }

        fun setNegative(setNegative: () -> Unit) = apply { negativeCallback = setNegative }

        fun setNeutral(setNeutral: () -> Unit) = apply { neutralCallback = setNeutral }

        fun cancelable(isCancelable: Boolean) = apply { this.isCancelable = isCancelable }

        fun theme(theme: Int) = apply { this.theme = theme }

        internal fun build(): MessageDialog = MessageDialog(this)
    }
}