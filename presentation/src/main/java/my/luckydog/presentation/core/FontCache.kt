package my.luckydog.presentation.core

import android.content.Context
import android.graphics.Typeface
import android.util.Log

class FontCache {

    companion object {
        private val TAG = FontCache::class.java.name

        private val fontCache = HashMap<String, Typeface>()

        fun getTypeFace(context: Context, fontName: String): Typeface {
            var typeface = fontCache[fontName]

            if (typeface == null) {
                try {
                    typeface = Typeface.createFromAsset(context.assets, "font/$fontName")
                } catch (ex: RuntimeException) {
                    Log.d(TAG, "failure get font with name $fontName from assets. Set default", ex)
                }
                if (typeface != null) fontCache[fontName] = typeface
            }

            return typeface ?: Typeface.DEFAULT
        }
    }
}