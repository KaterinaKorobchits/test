package my.luckydog.presentation.dialogs.input

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.Animatable
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import my.luckydog.presentation.R
import my.luckydog.presentation.core.extensions.*
import my.luckydog.presentation.databinding.DialogInputBinding
import my.luckydog.presentation.dialogs.buttonbar.ButtonDialogHandler

class InputDialog : Dialog {

    private lateinit var builder: Builder

    private val positive: () -> Unit = {
        builder.positiveCallback.invoke(builder.form.input.safeGet())
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

        val binding = DialogInputBinding.inflate(context.inflater(builder.theme))
            .apply {
                form = builder.form
                handler = ButtonDialogHandler(positive, negative, neutral)
                if (builder.isCancelable) background.setSafeOnClickListener { dismiss() }
            }
        setContentView(binding.root)

        window?.run {
            setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            fullScreen()
            background(R.color.dialog_background)
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

        internal lateinit var form: InputDialogForm
        internal lateinit var positiveCallback: (input: String) -> Unit
        internal lateinit var negativeCallback: () -> Unit
        internal lateinit var neutralCallback: () -> Unit
        internal var isAutoDismissNegative: Boolean = true
        internal var isAutoDismissPositive: Boolean = true
        internal var isAutoDismissNeutral: Boolean = true
        internal var isCancelable: Boolean = false
        internal var theme: Int = R.style.InputDialogTheme

        fun form(form: InputDialogForm) = apply { this.form = form }

        fun autoDismissNegative(dismiss: Boolean) = apply { isAutoDismissNegative = dismiss }

        fun autoDismissPositive(dismiss: Boolean) = apply { isAutoDismissPositive = dismiss }

        fun autoDismissNeutral(dismiss: Boolean) = apply { isAutoDismissNeutral = dismiss }

        fun setPositive(positive: (input: String) -> Unit) = apply { positiveCallback = positive }

        fun setNegative(negative: () -> Unit) = apply { negativeCallback = negative }

        fun setNeutral(setNeutral: () -> Unit) = apply { neutralCallback = setNeutral }

        fun cancelable(isCancelable: Boolean) = apply { this.isCancelable = isCancelable }

        fun theme(theme: Int) = apply { this.theme = theme }

        internal fun build(): InputDialog = InputDialog(this)
    }
}