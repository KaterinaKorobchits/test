package my.luckydog.data.bd

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import my.luckydog.data.bd.constants.LUCKYDOG_DATABASE
import my.luckydog.data.bd.constants.VERSION_DATABASE

@Database(entities = [User::class], version = VERSION_DATABASE)
abstract class LuckyDogDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

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
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
