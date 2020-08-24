package my.luckydog.presentation.core.extensions

import android.content.res.TypedArray
import android.graphics.Color

fun TypedArray.getDefinedResId(index: Int, defValue: Int = android.R.color.transparent): Int? {
    return if (hasValue(index)) getResourceId(index, defValue) else null
}

fun TypedArray.getDefinedBoolean(index: Int, defValue: Boolean = false): Boolean? {
    return if (hasValue(index)) getBoolean(index, defValue) else null
}

fun TypedArray.getDefinedColor(index: Int, defValue: Int = Color.TRANSPARENT): Int? {
    return if (hasValue(index)) getColor(index, defValue) else null
}

fun TypedArray.getDefinedDimensionPixel(index: Int, defValue: Int = 0): Int? {
    return if (hasValue(index)) getDimensionPixelSize(index, defValue) else null
}

fun TypedArray.getDefinedInt(index: Int, defValue: Int = 0): Int? {
    return if (hasValue(index)) getInt(index, defValue) else null
}