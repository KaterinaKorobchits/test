package my.luckydog.data.app.db.note

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Single
import my.luckydog.data.app.db.constants.TABLE_USER
import my.luckydog.data.app.db.constants.USER_ID

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: DbNote): Single<Long>

    @Delete
    fun delete(note: DbNote): Completable

    @Update
    fun update(notes: List<DbNote>): Completable

    @Transaction
    @Query("SELECT * FROM $TABLE_USER where $USER_ID = :userId LIMIT 1")
    fun getNotes(userId: Long): Single<UserWithNotes>
}