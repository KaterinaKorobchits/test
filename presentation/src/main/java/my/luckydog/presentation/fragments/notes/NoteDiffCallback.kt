package my.luckydog.presentation.fragments.notes

import my.luckydog.interactors.notes.ItemNote
import my.luckydog.presentation.core.BaseDiffCallback

class NoteDiffCallback(oldList: List<ItemNote>, newList: List<ItemNote>) :
    BaseDiffCallback<ItemNote>(oldList, newList) {

    override fun areItemsTheSame(oldItem: ItemNote, newItem: ItemNote): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ItemNote, newItem: ItemNote): Boolean {
        return oldItem.word == newItem.word
    }
}