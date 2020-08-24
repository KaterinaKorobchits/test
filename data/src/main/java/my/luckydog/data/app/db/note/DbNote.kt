package my.luckydog.data.app.db.note

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import my.luckydog.data.app.db.constants.*

@Entity(tableName = TABLE_NOTE)
data class DbNote(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = NOTE_ID)
    var id: Long = 0,

    @ColumnInfo(name = USER_ID)
    val userId: Long,

    @ColumnInfo(name = WORD)
    val word: String,

    @ColumnInfo(name = PRIORITY)
    val priority: Long = id
)