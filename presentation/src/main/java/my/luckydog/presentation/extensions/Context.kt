package my.luckydog.presentation.extensions

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.VectorDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.content.ContextCompat
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat

fun Context.inflater(): LayoutInflater = LayoutInflater.from(this)

fun Context.inflater(theme: Int): LayoutInflater =
    LayoutInflater.from(ContextThemeWrapper(this, theme))

fun Context.getBitmap(drawableResId: Int, size: Int): Bitmap{
    return when (val drawable = ContextCompat.getDrawable(this, drawableResId)) {
        is BitmapDrawable -> drawable.bitmap
        is VectorDrawableCompat, is VectorDrawable -> {
            val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            return bitmap
        }
        else -> throw IllegalArgumentException("Unsupported drawable type")
    }
}

fun Context.typedArray(attrs: AttributeSet, attr: IntArray): TypedArray = theme.obtainStyledAttributes(attrs, attr, 0, 0)