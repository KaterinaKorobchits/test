package my.luckydog.drawer

import androidx.databinding.ObservableField

data class DrawerForm(
    val isLock: ObservableField<Boolean> = ObservableField()
)