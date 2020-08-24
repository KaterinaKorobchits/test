package my.luckydog.presentation.dialogs.message

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.Animatable
import android.os.Bundle
import android.view.Window
import my.luckydog.presentation.R
import my.luckydog.presentation.core.extensions.background
import my.luckydog.presentation.core.extensions.fullScreen
import my.luckydog.presentation.core.extensions.inflater
import my.luckydog.presentation.core.extensions.setSafeOnClickListener
import my.luckydog.presentation.databinding.DialogMessageBinding
import my.luckydog.presentation.dialogs.buttonbar.ButtonDialogHandler

class MessageDialog : Dialog {

    private lateinit var builder: Builder

    private val positive: () -> Unit = {
        builder.positiveCallback.invoke()
        if (builder.isAutoDismissPositive) dismiss()
    }

    private val negative: () -> Unit = {
        builder.negativeCallback.invoke()
        if (builder.isAutoDismissNegative) dismiss()
    }

    private val neutral: () -> Unit = {
        builder.neutralCallback.invoke()
        if (builder.isAutoDismissNeutral) dismiss()
    }

    private constructor(context: Context, theme: Int) : super(context, theme)

    private constructor(builder: Builder) : this(builder.context, builder.theme) {
        this.builder = builder
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        val binding = DialogMessageBinding.inflate(context.inflater(builder.theme))
            .apply {
                form = builder.form
                handler = ButtonDialogHandler(positive, negative, neutral)
                if (builder.isCancelable) background.setSafeOnClickListener { dismiss() }
            }
        setContentView(binding.root)

        window?.run {
            fullScreen()
            background(android.R.color.transparent)
        }
    }

    override fun onStart() {
        super.onStart()
        (builder.form.iconDrawable.get() as? Animatable)?.start()
    }

    override fun hide() {
        super.hide()
        (builder.form.iconDrawable.get() as? Animatable)?.stop()
    }

    override fun onStop() {
        super.onStop()
        (builder.form.iconDrawable.get() as? Animatable)?.stop()
    }

    class Builder(internal val context: Context) {

        internal lateinit var form: MessageDialogForm
        internal var isAutoDismissNegative: Boolean = true
        internal var isAutoDismissPositive: Boolean = true
        internal var isAutoDismissNeutral: Boolean = true
        internal lateinit var positiveCallback: () -> Unit
        internal lateinit var negativeCallback: () -> Unit
        internal lateinit var neutralCallback: () -> Unit
        internal var isCancelable: Boolean = false
        internal var theme: Int = R.style.MessageDialogTheme

        fun form(form: MessageDialogForm) = apply { this.form = form }

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