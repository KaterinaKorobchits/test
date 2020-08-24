package my.luckydog.data.app.db.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import my.luckydog.data.app.db.constants.EMAIl
import my.luckydog.data.app.db.constants.PASSWORD
import my.luckydog.data.app.db.constants.TABLE_USER
import my.luckydog.data.app.db.constants.USER_ID

@Entity(tableName = TABLE_USER)
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = USER_ID)
    var userId: Long = 0,

    @ColumnInfo(name = EMAIl)
    val email: String,

    @ColumnInfo(name = PASSWORD)
    val password: String
)