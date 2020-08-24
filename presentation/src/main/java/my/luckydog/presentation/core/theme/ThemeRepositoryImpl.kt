package my.luckydog.presentation.core.theme

import android.app.Activity
import my.luckydog.presentation.R
import javax.inject.Inject

class ThemeRepositoryImpl @Inject constructor(private val store: ThemeStore) : ThemeRepository {

    override fun apply(activity: Activity) {
        activity.setTheme(if (store.getCurrent() == 0) store.getDefault() else store.getCurrent())
    }

    override fun apply(activity: Activity, theme: Int, isRecreate: Boolean) {
        if (store.getCurrent() == theme) return

        activity.setTheme(theme)
        store.store(theme)

        if (isRecreate) activity.recreate()
    }

    override fun current(): Int = store.getCurrent()

    override fun datePickerDialog() = R.style.DatePickerDialogTheme

    override fun timePickerDialog() = R.style.TimePickerDialogTheme

    override fun progressDialog() = R.style.ProgressDialogTheme

    override fun messageDialog() = R.style.MessageDialogTheme

    override fun inputDialog() = R.style.InputDialogTheme
}