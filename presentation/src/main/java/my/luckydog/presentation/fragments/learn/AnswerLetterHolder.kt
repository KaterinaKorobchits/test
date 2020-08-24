package my.luckydog.presentation.fragments.learn

import androidx.recyclerview.widget.RecyclerView
import my.luckydog.presentation.databinding.ItemAnswerLetterBinding

class AnswerLetterHolder(
    private val binding: ItemAnswerLetterBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ItemLetter) {
        binding.form = item
    }
}