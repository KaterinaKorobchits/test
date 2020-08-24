package my.luckydog.domain.notes

import my.luckydog.boundaries.notes.Note
import my.luckydog.common.Mapper
import my.luckydog.interactors.notes.ItemNote
import javax.inject.Inject

class NoteMapper @Inject constructor() : Mapper<ItemNote, Note> {

    override fun transform(entity: ItemNote): Note = with(entity) { Note(id, word, priority) }
}