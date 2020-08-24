package my.luckydog.presentation.fragments.notes

import androidx.recyclerview.widget.RecyclerView
import my.luckydog.interactors.notes.ItemNote
import my.luckydog.presentation.databinding.ItemNoteBinding

class NoteHolder(private val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ItemNote) {
        binding.form = item
    }
}