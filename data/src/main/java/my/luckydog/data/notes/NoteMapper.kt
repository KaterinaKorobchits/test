package my.luckydog.data.notes

import my.luckydog.boundaries.notes.Note
import my.luckydog.common.Mapper
import my.luckydog.data.app.db.note.DbNote
import javax.inject.Inject

class NoteMapper @Inject constructor() : Mapper<DbNote, Note> {

    init {
        println("!!!init: ${this::class.java.name} ${this.hashCode()}")
    }

    override fun transform(entity: DbNote): Note = with(entity) { Note(id, word, priority) }
}