package my.luckydog.data.app.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import my.luckydog.data.app.db.constants.LUCKYDOG_DATABASE
import my.luckydog.data.app.db.constants.VERSION_DATABASE
import my.luckydog.data.app.db.note.DbNote
import my.luckydog.data.app.db.note.NoteDao
import my.luckydog.data.app.db.user.User
import my.luckydog.data.app.db.user.UserDao
import my.luckydog.data.app.db.word.DbWord

@Database(entities = [User::class, DbNote::class, DbWord::class], version = VERSION_DATABASE)
abstract class LuckyDogDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun noteDao(): NoteDao

    companion object {
        @Volatile
        private var INSTANCE: LuckyDogDatabase? = null

        fun getDatabase(context: Context): LuckyDogDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) return tempInstance

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LuckyDogDatabase::class.java,
                    LUCKYDOG_DATABASE
                ).createFromAsset("database/default").build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
