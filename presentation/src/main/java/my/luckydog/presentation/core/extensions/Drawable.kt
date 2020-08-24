package my.luckydog.presentation.core.extensions

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable

fun Drawable.toBitmap(width: Int = intrinsicWidth, height: Int = intrinsicHeight) = when {
    this is BitmapDrawable -> bitmap
    width > 0 && height > 0 -> {
        val created = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(created)
        setBounds(0, 0, canvas.width, canvas.height)
        draw(canvas)
        created
    }
    else -> null
}