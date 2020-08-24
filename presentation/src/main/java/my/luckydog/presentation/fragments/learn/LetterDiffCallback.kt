package my.luckydog.presentation.fragments.learn

import my.luckydog.presentation.core.BaseDiffCallback

class LetterDiffCallback(oldList: List<ItemLetter>, newList: List<ItemLetter>) :
    BaseDiffCallback<ItemLetter>(oldList, newList) {

    override fun areItemsTheSame(oldItem: ItemLetter, newItem: ItemLetter): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ItemLetter, newItem: ItemLetter): Boolean {
        return oldItem.letter == newItem.letter
    }
}