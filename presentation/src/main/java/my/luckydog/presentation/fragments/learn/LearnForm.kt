package my.luckydog.presentation.fragments.learn

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList

data class LearnForm(
    val letters: ObservableList<ItemLetter> = ObservableArrayList(),
    val answerLetters: ObservableList<ItemLetter> = ObservableArrayList()
)