package modulo_06.ej06

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import modulo_06.ej06.room.DbDataSource
import modulo_06.ej06.room.DbMovie
import modulo_06.ej06.room.DbMovieDao
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.hasItem
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Matchers.equalTo
import org.hamcrest.beans.HasPropertyWithValue.hasProperty
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DbMovieDaoTest {

    private lateinit var database: DbDataSource
    private lateinit var dao: DbMovieDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, DbDataSource::class.java)
            .allowMainThreadQueries() // Allow queries on the main thread for testing
            .build()
        dao = database.moviesDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertMovie_insertsDataCorrectly() = runTest {
        val movie = DbMovie(
            id = 1,
            title = "Movie Title",
            overview = "Movie Overview",
            poster_path = "poster_path"
        )
        dao.insert(movie)
        val retrievedMovies = dao.getAll().first()
        val retrievedMovie =
            retrievedMovies.find {
                it.title == movie.title &&
                        it.overview == movie.overview &&
                        it.poster_path == movie.poster_path
            }

        assertNotNull(retrievedMovie)
    }

    @Test
    fun deleteMovie_deletesDataCorrectly() = runTest {
        val movie = DbMovie(
            id = 1,
            title = "Movie Title",
            overview = "Movie Overview",
            poster_path = "poster_path"
        )
        dao.insert(movie)

        // Delete the movie
        dao.delete(movie)

        // Verify the movie is deleted
        val retrievedMoviesAfterDeletion = dao.getAll().first()
        assertThat(retrievedMoviesAfterDeletion, not(hasItem(movie)))
}



}