package my.luckydog.presentation.fragments.home

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import my.luckydog.presentation.core.BaseAdapter
import my.luckydog.presentation.core.extensions.inflater
import my.luckydog.presentation.databinding.ItemBinding
import javax.inject.Inject

class ItemAdapter @Inject constructor() : BaseAdapter<ItemForm>() {

    override val data = ArrayList<ItemForm>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(ItemBinding.inflate(parent.inflater()))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemHolder).bind(data[position])
    }

    override fun diffCallback(old: List<ItemForm>, new: List<ItemForm>) = ItemDiffCallback(old, new)
}