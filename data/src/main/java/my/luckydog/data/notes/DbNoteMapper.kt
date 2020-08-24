package my.luckydog.data.notes

import my.luckydog.boundaries.app.session.SessionStore
import my.luckydog.boundaries.notes.Note
import my.luckydog.common.Mapper
import my.luckydog.data.app.db.note.DbNote
import javax.inject.Inject

class DbNoteMapper @Inject constructor(private val session: SessionStore) : Mapper<Note, DbNote> {

    init {
        println("!!!init: ${this::class.java.name} ${this.hashCode()}")
    }

    override fun transform(entity: Note): DbNote {
        return with(entity) { DbNote(id, session.getUserId(), word, priority) }
    }
}