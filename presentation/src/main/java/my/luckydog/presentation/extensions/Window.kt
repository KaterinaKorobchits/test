package my.luckydog.presentation.extensions

import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.Window

fun Window.fullScreen() = setLayout(MATCH_PARENT, MATCH_PARENT)

fun Window.background(res: Int) = setBackgroundDrawableResource(res)