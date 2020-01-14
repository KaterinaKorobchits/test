package my.luckydog.presentation.utils

import android.graphics.drawable.Drawable
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout
import my.luckydog.presentation.dialogs.buttonbar.ButtonBarDialog
import my.luckydog.presentation.dialogs.buttonbar.ButtonDialogForm
import my.luckydog.presentation.dialogs.buttonbar.ButtonDialogHandler

@BindingAdapter("errorText")
fun TextInputLayout.setErrorMessage(errorMessage: String?) {
    error = errorMessage
}

@BindingAdapter("srcIcon", "drawableIcon", requireAll = false)
fun AppCompatImageView.setImageDrawable(resource: Int?, drawable: Drawable?) = when {
    drawable != null -> setImageDrawable(drawable)
    resource != null -> setImageResource(resource)
    else -> visibility = View.GONE
}

@BindingAdapter("handler")
fun ButtonBarDialog.setHandler(handler: ButtonDialogHandler) = handler(handler)

@BindingAdapter("form")
fun ButtonBarDialog.setForm(form: ButtonDialogForm) = form(form)