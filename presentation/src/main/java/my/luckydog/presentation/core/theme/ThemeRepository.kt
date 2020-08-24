package my.luckydog.presentation.core.theme

import android.app.Activity

interface ThemeRepository {

    fun apply(activity: Activity)

    fun apply(activity: Activity, theme: Int, isRecreate: Boolean)

    fun current(): Int

    fun datePickerDialog(): Int

    fun timePickerDialog(): Int

    fun progressDialog(): Int

    fun messageDialog(): Int

    fun inputDialog(): Int
}