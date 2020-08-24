package my.luckydog.presentation.views

import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import my.luckydog.presentation.core.BaseScaleType
import java.io.File

interface LoadImage {

    fun load(url: String)

    fun load(file: File)

    fun load(drawable: Drawable)

    fun load(resourceId: Int)

    fun setFailure(file: File): LoadImage

    fun setFailure(drawable: Drawable): LoadImage

    fun setFailure(resourceId: Int): LoadImage

    fun setPlaceholder(file: File): LoadImage

    fun setPlaceholder(drawable: Drawable): LoadImage

    fun setPlaceholder(resourceId: Int): LoadImage

    fun setScaleType(scaleType: BaseScaleType): LoadImage

    fun asCircle(isCircle: Boolean): LoadImage

    fun setCorners(radius: Float): LoadImage

    fun setCorners(@DimenRes radiusRes: Int): LoadImage

    fun setCorners(@DimenRes topLeft: Int, @DimenRes topRight: Int, @DimenRes bottomRight: Int, @DimenRes bottomLeft: Int): LoadImage

    fun setCorners(
        topLeft: Float,
        topRight: Float,
        bottomRight: Float,
        bottomLeft: Float
    ): LoadImage

    fun setBorderWidth(width: Float): LoadImage

    fun setBorderWidthRes(@DimenRes widthRes: Int): LoadImage

    fun setBorderColor(@ColorInt color: Int): LoadImage

    fun setBorderColorRes(@ColorRes colorRes: Int): LoadImage
}

