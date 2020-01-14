package my.luckydog.data.utils

import android.content.SharedPreferences
import java.lang.Double.doubleToRawLongBits
import java.lang.Double.longBitsToDouble

class PropertiesStoreImpl(private val prefs: SharedPreferences) :
    PropertiesStore {

    private val editor: SharedPreferences.Editor by lazy { prefs.edit() }

    override fun getBoolean(key: String, defValue: Boolean) = prefs.getBoolean(key, defValue)

    override fun putBoolean(key: String, value: Boolean) = editor.putBoolean(key, value).apply()

    override fun getInt(key: String, defValue: Int) = prefs.getInt(key, defValue)

    override fun putInt(key: String, value: Int) = editor.putInt(key, value).apply()

    override fun getDouble(key: String, defValue: Double): Double {
        return longBitsToDouble(getLong(key, doubleToRawLongBits(defValue)))
    }

    override fun putDouble(key: String, value: Double) {
        editor.putLong(key, doubleToRawLongBits(value)).apply()
    }

    override fun getLong(key: String, defValue: Long) = prefs.getLong(key, defValue)

    override fun putLong(key: String, value: Long) = editor.putLong(key, value).apply()

    override fun getFloat(key: String, defValue: Float) = prefs.getFloat(key, defValue)

    override fun putFloat(key: String, value: Float) = editor.putFloat(key, value).apply()

    override fun getString(key: String, defValue: String) = prefs.getString(key, defValue) ?: ""

    override fun putString(key: String, value: String) = editor.putString(key, value).apply()

    override fun getStringSet(key: String, defValue: Set<String>): Set<String> {
        return prefs.getStringSet(key, defValue) ?: emptySet()
    }

    override fun putStringSet(key: String, value: Set<String>) {
        editor.putStringSet(key, value).apply()
    }

    override fun clear() = editor.clear().apply()

    override fun remove(key: String) = editor.remove(key).apply()
}