package my.luckydog.presentation.core.extensions

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.TypedArray
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.VectorDrawable
import android.net.ConnectivityManager
import android.net.Uri
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.content.ContextCompat
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import java.io.File

fun Context.inflater(): LayoutInflater = LayoutInflater.from(this)

fun Context.inflater(theme: Int): LayoutInflater =
    LayoutInflater.from(ContextThemeWrapper(this, theme))

fun Context.getBitmap(drawableResId: Int, size: Int): Bitmap {
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

fun Context.typedArray(attrs: AttributeSet, attr: IntArray): TypedArray {
    return theme.obtainStyledAttributes(attrs, attr, 0, 0)
}

fun Context.getInputMethodManager(): InputMethodManager {
    return getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
}

fun Context.getConnectivityManager(): ConnectivityManager {
    return applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
}

fun Context.grantUriPermissions(intent: Intent, uri: Uri, flags: Int) {
    packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
        .forEach { grantUriPermission(it.activityInfo.packageName, uri, flags) }
}

fun Context.createCacheDir(name: String): File? = cacheDir.createDir(name)

fun Context.dpFromPx(px: Float): Int = (px / resources.displayMetrics.density).toInt()

fun Context.pxFromDp(dp: Float) = dp * resources.displayMetrics.density
