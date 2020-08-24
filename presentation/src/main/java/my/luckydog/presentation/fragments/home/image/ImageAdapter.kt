package my.luckydog.presentation.fragments.home.image

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import my.luckydog.interactors.core.image.ItemImage
import my.luckydog.presentation.core.extensions.inflater
import my.luckydog.presentation.databinding.ItemImageBinding
import javax.inject.Inject

class ImageAdapter @Inject constructor() : PagedListAdapter<ItemImage, ImageHolder>(diffCallback) {

    companion object {

        private val diffCallback = object : DiffUtil.ItemCallback<ItemImage>() {

            override fun areItemsTheSame(old: ItemImage, new: ItemImage) = old.id == new.id

            override fun areContentsTheSame(old: ItemImage, new: ItemImage) = old.url == new.url
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        return ImageHolder(ItemImageBinding.inflate(parent.inflater()))
    }

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        getItem(position)?.let { (holder).bind(it) }
    }
}