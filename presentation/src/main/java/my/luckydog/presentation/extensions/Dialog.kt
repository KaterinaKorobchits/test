package my.luckydog.presentation.extensions

import android.app.Dialog
import android.content.Context
import android.view.ContextThemeWrapper

fun Dialog.activityContext(): Context = (context as ContextThemeWrapper).baseContext