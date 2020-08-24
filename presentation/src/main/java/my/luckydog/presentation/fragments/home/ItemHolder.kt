package my.luckydog.presentation.fragments.home

import androidx.recyclerview.widget.RecyclerView
import my.luckydog.presentation.databinding.ItemBinding

class ItemHolder(private val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ItemForm) {
        binding.form = item
    }
}