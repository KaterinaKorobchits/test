package my.luckydog.presentation.fragments.learn

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import my.luckydog.presentation.core.BaseAdapter
import my.luckydog.presentation.core.extensions.inflater
import my.luckydog.presentation.databinding.ItemAnswerLetterBinding
import javax.inject.Inject

class AnswerAdapter @Inject constructor() : BaseAdapter<ItemLetter>() {

    override val data = ArrayList<ItemLetter>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemAnswerLetterBinding.inflate(parent.inflater())
        binding.root.setOnTouchListener(TouchWrapperLongClick())
        return AnswerLetterHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.visibility = View.VISIBLE
        (holder as AnswerLetterHolder).bind(data[position])
    }

    override fun diffCallback(old: List<ItemLetter>, new: List<ItemLetter>): DiffUtil.Callback {
        return LetterDiffCallback(old, new)
    }
}