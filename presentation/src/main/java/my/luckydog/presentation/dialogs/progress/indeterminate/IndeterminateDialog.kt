package my.luckydog.presentation.dialogs.progress.indeterminate

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import my.luckydog.presentation.R
import my.luckydog.presentation.databinding.DialogIndeterminateBinding
import my.luckydog.presentation.extensions.background
import my.luckydog.presentation.extensions.fullScreen
import my.luckydog.presentation.extensions.inflater

class IndeterminateDialog : Dialog {

    private lateinit var builder: Builder

    private constructor(context: Context, theme: Int) : super(context, theme)

    private constructor(builder: Builder) : this(builder.context, builder.theme) {
        this.builder = builder
        setCancelable(builder.isCancelable)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        val binding = DialogIndeterminateBinding.inflate(context.inflater(builder.theme))
            .apply { form = builder.form }
        setContentView(binding.root)

        window?.run {
            fullScreen()
            background(android.R.color.transparent)
        }
    }

    class Builder(internal val context: Context) {

        internal lateinit var form: IndeterminateDialogForm
        internal var isCancelable: Boolean = false
        internal var theme: Int = R.style.ProgressDialogTheme

        fun form(form: IndeterminateDialogForm) = apply { this.form = form }

        fun cancelable(isCancelable: Boolean) = apply { this.isCancelable = isCancelable }

        fun theme(theme: Int) = apply { this.theme = theme }

        internal fun build(): IndeterminateDialog = IndeterminateDialog(this)
    }
}