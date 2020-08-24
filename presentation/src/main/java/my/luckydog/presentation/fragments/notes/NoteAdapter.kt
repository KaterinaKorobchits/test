package my.luckydog.presentation.fragments.notes

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.asFlow
import my.luckydog.interactors.notes.ItemNote
import my.luckydog.presentation.core.BaseAdapter
import my.luckydog.presentation.core.extensions.inflater
import my.luckydog.presentation.databinding.ItemNoteBinding
import javax.inject.Inject

class NoteAdapter @Inject constructor() : BaseAdapter<ItemNote>() {

    override val data = ArrayList<ItemNote>()

    private var click: (ItemNote) -> Unit = {}

    fun setClick(click: (ItemNote) -> Unit) {
        this.click = click
    }

    private val clicks = BroadcastChannel<ItemNote>(100)

    fun clickFlow() = clicks.asFlow()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemNoteBinding.inflate(parent.inflater(), parent, false)
        return NoteHolder(binding).apply {

            binding.root.setOnClickListener {
                if (adapterPosition != NO_POSITION){
                    val a = clicks.offer(data[adapterPosition])
                    println()
                }
                    //click.invoke(data[adapterPosition])
                println()
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as NoteHolder).bind(data[position])
    }

    override fun diffCallback(old: List<ItemNote>, new: List<ItemNote>) = NoteDiffCallback(old, new)
}