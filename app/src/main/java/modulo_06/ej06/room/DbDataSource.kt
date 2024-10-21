package modulo_06.ej06.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DbMovie::class], version = 1)
abstract class DbDataSource: RoomDatabase() {
    abstract fun moviesDao(): DbMovieDao
}

