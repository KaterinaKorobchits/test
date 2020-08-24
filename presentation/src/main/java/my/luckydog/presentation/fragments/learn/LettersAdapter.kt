package my.luckydog.presentation.fragments.learn

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import my.luckydog.presentation.core.BaseAdapter
import my.luckydog.presentation.core.extensions.inflater
import my.luckydog.presentation.core.extensions.setSafeOnClickListener
import my.luckydog.presentation.databinding.ItemLetterBinding
import my.luckydog.presentation.databinding.ItemLetterChosenBinding
import javax.inject.Inject

class LettersAdapter @Inject constructor() : BaseAdapter<ItemLetter>() {

    private companion object {
        const val NOT_CHOSEN_TYPE = 1
        const val CHOSEN_TYPE = 2
    }

    override val data = ArrayList<ItemLetter>()

    private var click: (ItemLetter) -> Unit = {}

    fun setClick(click: (ItemLetter) -> Unit) {
        this.click = click
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            CHOSEN_TYPE -> {
                val binding = ItemLetterChosenBinding.inflate(parent.inflater())
                binding.root.setOnTouchListener(null)
                LetterChosenHolder(binding)
            }
            else -> {
                val binding = ItemLetterBinding.inflate(parent.inflater())
                val holder = LetterHolder(binding)
                binding.root.setSafeOnClickListener {
                    if (holder.adapterPosition != RecyclerView.NO_POSITION)
                        click.invoke(data[holder.adapterPosition])
                }
                binding.root.setOnTouchListener(TouchWrapper())
                holder
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is LetterHolder -> holder.bind(data[position])
            is LetterChosenHolder -> holder.bind(data[position] as ItemLetterChosen)
        }
    }

    override fun getItemViewType(position: Int) = when (data[position]) {
        is ItemLetterChosen -> CHOSEN_TYPE
        else -> NOT_CHOSEN_TYPE
    }

    override fun diffCallback(old: List<ItemLetter>, new: List<ItemLetter>): DiffUtil.Callback {
        return LetterDiffCallback(old, new)
    }
}