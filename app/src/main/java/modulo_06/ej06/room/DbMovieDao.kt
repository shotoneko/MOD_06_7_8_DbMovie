package modulo_06.ej06.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DbMovieDao {

    @Query("SELECT * FROM tab_movies ORDER BY MovieId DESC")
    fun getAll(): Flow<List<DbMovie>>

    @Insert
    fun insert(movie: DbMovie)

    @Delete
    fun delete(movie: DbMovie)

}
