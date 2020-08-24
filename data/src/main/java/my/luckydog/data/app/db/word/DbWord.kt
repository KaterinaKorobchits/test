package my.luckydog.data.app.db.word

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import my.luckydog.data.app.db.constants.*

@Entity(tableName = TABLE_WORD)
data class DbWord(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = WORD_ID)
    var id: Long = 0,

    @ColumnInfo(name = WORD)
    val word: String,

    @ColumnInfo(name = TRANSLATE)
    val translate: String,

    @ColumnInfo(name = TRANSCRIPTION)
    val transcription: String,

    @ColumnInfo(name = IMAGE)
    val image: String,

    @ColumnInfo(name = EXAMPLE)
    val example: String
)