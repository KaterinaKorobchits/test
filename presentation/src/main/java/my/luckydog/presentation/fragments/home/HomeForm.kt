package my.luckydog.presentation.fragments.home

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.databinding.ObservableList

data class HomeForm(
    val recyclerData: ObservableList<ItemForm> = ObservableArrayList(),
    val search: ObservableField<String> = ObservableField()
)