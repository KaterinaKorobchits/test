package my.luckydog.presentation.core.theme

import my.luckydog.common.PropertiesStore
import my.luckydog.presentation.R
import javax.inject.Inject

class ThemeStore @Inject constructor(private val propertiesStore: PropertiesStore) {

    private companion object {
        private const val CURRENT_THEME = "current_theme"
    }

    fun getDefault() = R.style.AppTheme_LightTheme

    fun getCurrent() = propertiesStore.getInt(CURRENT_THEME)

    fun store(theme: Int) = propertiesStore.putInt(CURRENT_THEME, theme)

    fun clear() {
        store(getDefault())
    }
}