package my.luckydog.presentation.core.extensions

import android.app.Activity

fun Activity.hideSoftKeyboard() {
    if (currentFocus != null) {
        val inputMethodManager = getInputMethodManager()
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }
}