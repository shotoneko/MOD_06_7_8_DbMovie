package modulo_06.ej06.repository

import android.util.Log
import kotlinx.coroutines.flow.Flow
import modulo_06.ej06.retrofit.Movie
import modulo_06.ej06.retrofit.RestDataSource
import modulo_06.ej06.room.DbMovie
import modulo_06.ej06.room.DbMovieDao
import javax.inject.Inject

interface Repository {
    suspend fun getAllMoviesApi(): List<Movie>
    fun getAllMoviesDb(): Flow<List<DbMovie>>
    suspend fun deleteDbMovie(toDelete: DbMovie)
    suspend fun getNewMovie(): DbMovie?
}

class RepositoryImp @Inject constructor(
    private val apiSource: RestDataSource,
    private val dbSource: DbMovieDao
) : Repository {

    private var movieIndex = 0 // Counter variable

    override suspend fun getNewMovie(): DbMovie? {

        return apiSource.getNewMovie().body()?.results?.getOrNull(movieIndex)?.let { movie ->
            Log.d("MovieRepository", "Movie Title: ${movie.title}")

            movieIndex += 1

            with(movie){
                val dbMovie = DbMovie(
                    id = id,
                    title = title,
                    overview = overview,
                    poster_path = poster_path
                )
                dbSource.insert(dbMovie)
                dbMovie
            }
        }
    }

    override suspend fun getAllMoviesApi(): List<Movie> {
        val response = apiSource.getAllMovies()
        if (response.isSuccessful) {
            val movies = response.body()?.results
            movies?.forEach() { movie ->
                Log.d("MovieRepository", "Movie Title: ${movie.title}")
            }
            return movies!!
        } else {
            return emptyList()
        }
    }


    override suspend fun deleteDbMovie(toDelete: DbMovie) = dbSource.delete(toDelete)

    override fun getAllMoviesDb(): Flow<List<DbMovie>> = dbSource.getAll()
}

