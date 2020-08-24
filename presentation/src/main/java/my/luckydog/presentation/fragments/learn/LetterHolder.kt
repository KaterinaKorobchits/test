package my.luckydog.presentation.fragments.learn

import androidx.recyclerview.widget.RecyclerView
import my.luckydog.presentation.databinding.ItemLetterBinding

class LetterHolder(private val binding: ItemLetterBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ItemLetter) {
        binding.form = item
    }
}