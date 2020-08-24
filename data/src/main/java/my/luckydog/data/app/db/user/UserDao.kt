package my.luckydog.data.app.db.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single
import my.luckydog.data.app.db.constants.EMAIl
import my.luckydog.data.app.db.constants.PASSWORD
import my.luckydog.data.app.db.constants.TABLE_USER
import my.luckydog.data.app.db.constants.USER_ID

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: User): Single<Long>

    @Query("SELECT EXISTS(SELECT 1 FROM $TABLE_USER where $EMAIl = :email LIMIT 1)")
    fun isExistEmail(email: String): Single<Int>

    @Query("SELECT EXISTS(SELECT 1 FROM $TABLE_USER where $EMAIl = :email and $PASSWORD = :password LIMIT 1)")
    fun isExistUser(email: String, password: String): Single<Int>

    @Query("SELECT $USER_ID FROM $TABLE_USER where $EMAIl = :email LIMIT 1")
    fun getUserId(email: String): Single<Long>
}