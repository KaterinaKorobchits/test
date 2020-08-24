package my.luckydog.presentation.core.extensions

import androidx.databinding.ObservableField

fun ObservableField<String>.safeGet(defValue: String = ""): String = get() ?: defValue

fun <T> ObservableField<T>.safeGet(defValue: T): T = get() ?: defValue