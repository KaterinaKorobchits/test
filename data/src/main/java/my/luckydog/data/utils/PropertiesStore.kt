package my.luckydog.data.utils

interface PropertiesStore {

    fun getBoolean(key: String, defValue: Boolean = false): Boolean

    fun putBoolean(key: String, value: Boolean)

    fun getInt(key: String, defValue: Int = 0): Int

    fun putInt(key: String, value: Int)

    fun getDouble(key: String, defValue: Double = 0.0): Double

    fun putDouble(key: String, value: Double)

    fun getLong(key: String, defValue: Long = 0L): Long

    fun putLong(key: String, value: Long)

    fun getString(key: String, defValue: String = ""): String

    fun putString(key: String, value: String)

    fun getFloat(key: String, defValue: Float = 0.0f): Float

    fun putFloat(key: String, value: Float)

    fun getStringSet(key: String, defValue: Set<String> = emptySet()): Set<String>

    fun putStringSet(key: String, value: Set<String>)

    fun clear()

    fun remove(key: String)
}