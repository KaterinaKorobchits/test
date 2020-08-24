package my.luckydog.data.notes

import my.luckydog.boundaries.notes.Note
import my.luckydog.boundaries.notes.NotesRepository
import my.luckydog.common.Mapper
import my.luckydog.data.app.db.note.DbNote
import my.luckydog.data.app.db.note.NoteDao
import javax.inject.Inject

class NotesRepositoryImpl @Inject constructor(
    private val dao: NoteDao,
    private val mapper: Mapper<DbNote, Note>,
    private val dbMapper: Mapper<Note, DbNote>
) : NotesRepository {

    init {
        println("!!!init: ${this::class.java.name} ${this.hashCode()}")
    }

    override fun add(word: String, userId: Long) = dao.insert(DbNote(word = word, userId = userId))

    override fun delete(note: Note) = dao.delete(dbMapper.transform(note))

    override fun update(notes: List<Note>) = dao.update(notes.map { dbMapper.transform(it) }.also { println("NotesRepositoryImpl - $it") })

    override fun getAll(userId: Long) = dao.getNotes(userId)
        .map { it.note.map { note -> mapper.transform(note) } }
}