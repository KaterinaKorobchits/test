package my.luckydog.domain.notes

import my.luckydog.boundaries.notes.Note
import my.luckydog.common.Mapper
import my.luckydog.interactors.notes.ItemNote
import javax.inject.Inject

class ItemNoteMapper @Inject constructor() : Mapper<Note, ItemNote> {

    override fun transform(entity: Note): ItemNote = with(entity) { ItemNote(id, word, priority) }
}