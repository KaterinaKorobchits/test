package my.luckydog.presentation.fragments.home

import my.luckydog.presentation.core.BaseDiffCallback

class ItemDiffCallback(oldList: List<ItemForm>, newList: List<ItemForm>) :
    BaseDiffCallback<ItemForm>(oldList, newList) {

    override fun areItemsTheSame(oldItem: ItemForm, newItem: ItemForm): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ItemForm, newItem: ItemForm): Boolean {
        return oldItem.title == newItem.title
    }
}