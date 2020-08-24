package my.luckydog.presentation.core.extensions

import android.app.Dialog
import android.content.Context
import android.view.ContextThemeWrapper

fun Dialog.activityContext(): Context = (context as ContextThemeWrapper).baseContext

fun Dialog.hideSoftKeyboard() {
    if (currentFocus != null) {
        val inputMethodManager = context.getInputMethodManager()
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }
}

