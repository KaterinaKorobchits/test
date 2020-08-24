package my.luckydog.presentation.fragments.settings

import androidx.databinding.ObservableBoolean

data class SettingsForm(
    val isDarkTheme: ObservableBoolean = ObservableBoolean()
)