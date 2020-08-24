package my.luckydog.drawer

import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import my.luckydog.R

data class DrawerHeaderForm(
    val email: ObservableField<String> = ObservableField(),
    val iconRes: ObservableInt = ObservableInt(R.drawable.ic_pets_black_24dp)
)