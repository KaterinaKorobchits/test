package my.luckydog.presentation.fragments.learn

import androidx.recyclerview.widget.RecyclerView
import my.luckydog.presentation.databinding.ItemLetterChosenBinding

class LetterChosenHolder(
    private val binding: ItemLetterChosenBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ItemLetterChosen) {
        binding.form = item
    }
}