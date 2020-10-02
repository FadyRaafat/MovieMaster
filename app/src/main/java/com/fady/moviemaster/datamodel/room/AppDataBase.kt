package com.fady.moviemaster.datamodel.room
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.fady.moviemaster.datamodel.models.Movie

@Database(entities = [Movie::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDataBase : RoomDatabase() {

    abstract fun getMovie(): MovieDAO

    companion object {
        private const val DB_NAME = "moviemaster"

        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getDatabase(context: Context): AppDataBase? {
            if (INSTANCE == null) {
                synchronized(AppDataBase::class.java) {

                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDataBase::class.java, DB_NAME
                    )
                        .allowMainThreadQueries().build()
                }
            }
            return INSTANCE
        }
    }


}
