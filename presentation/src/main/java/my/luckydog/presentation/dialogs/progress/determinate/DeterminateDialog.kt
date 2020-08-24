package my.luckydog.presentation.dialogs.progress.determinate

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import my.luckydog.presentation.R
import my.luckydog.presentation.core.extensions.background
import my.luckydog.presentation.core.extensions.fullScreen
import my.luckydog.presentation.core.extensions.inflater
import my.luckydog.presentation.core.extensions.setSafeOnClickListener
import my.luckydog.presentation.databinding.DialogDeterminateBinding

class DeterminateDialog : Dialog {

    private lateinit var builder: Builder

    private constructor(context: Context, theme: Int) : super(context, theme)

    private constructor(builder: Builder) : this(builder.context, builder.theme) {
        this.builder = builder
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        val binding = DialogDeterminateBinding.inflate(context.inflater(builder.theme))
            .apply {
                form = builder.form
                if (builder.isCancelable) background.setSafeOnClickListener { dismiss() }
            }
        setContentView(binding.root)

        window?.run {
            fullScreen()
            background(android.R.color.transparent)
        }
    }

    class Builder(internal val context: Context) {

        internal lateinit var form: DeterminateDialogForm
        internal var isCancelable: Boolean = false
        internal var theme: Int = R.style.ProgressDialogTheme

        fun form(form: DeterminateDialogForm) = apply { this.form = form }

        fun cancelable(isCancelable: Boolean) = apply { this.isCancelable = isCancelable }

        fun theme(theme: Int) = apply { this.theme = theme }

        internal fun build(): DeterminateDialog = DeterminateDialog(this)
    }
}