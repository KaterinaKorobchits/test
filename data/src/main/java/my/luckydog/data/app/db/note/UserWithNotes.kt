package my.luckydog.data.app.db.note

import androidx.room.Embedded
import androidx.room.Relation
import my.luckydog.data.app.db.constants.USER_ID
import my.luckydog.data.app.db.user.User

data class UserWithNotes(
    @Embedded
    val user: User,

    @Relation(parentColumn = USER_ID, entityColumn = USER_ID)
    val note: List<DbNote>
)