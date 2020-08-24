package my.luckydog.presentation.fragments.about

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableList
import my.luckydog.interactors.about.ItemChip

data class AboutForm(
    val chips: ObservableField<List<ItemChip>> = ObservableField(emptyList())
)