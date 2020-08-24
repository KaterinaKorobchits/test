package my.luckydog.presentation.fragments.notes

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import my.luckydog.interactors.notes.ItemNote

data class NotesForm(
    val notes: ObservableList<ItemNote> = ObservableArrayList()
)