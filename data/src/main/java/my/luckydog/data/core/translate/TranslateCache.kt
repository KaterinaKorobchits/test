package my.luckydog.data.core.translate

import android.util.LruCache

class TranslateCache (maxSize: Int) : LruCache<String, List<String>>(maxSize)