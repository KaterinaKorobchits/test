package my.luckydog.presentation.fragments.home.image

import androidx.recyclerview.widget.RecyclerView
import my.luckydog.interactors.core.image.ItemImage
import my.luckydog.presentation.databinding.ItemImageBinding

class ImageHolder(private val binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ItemImage) {
        binding.form = item
    }
}