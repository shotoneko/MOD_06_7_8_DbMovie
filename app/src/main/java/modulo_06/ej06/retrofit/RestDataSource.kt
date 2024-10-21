package modulo_06.ej06.retrofit

import androidx.room.Query
import modulo_06.ej06.utils.Constants.Companion.ENDPOINT_LOCATION
import modulo_06.ej06.utils.Constants.Companion.ENDPOINT_NAME
import modulo_06.ej06.utils.Constants.Companion.ENDPOINT_PICTURE
import retrofit2.Response
import retrofit2.http.GET

interface RestDataSource {
    companion object {
        const val ENDPOINT_NOW_PLAYING = "movie/now_playing"
        const val ENDPOINT_MOVIE_SINGLE = "movie/now_playing?api_key=948806a28761e2341bcc9efb5471d919"
    }

    @GET(ENDPOINT_NOW_PLAYING)
    suspend fun getAllMovies(
        @retrofit2.http.Query("language") language: String = "en-US",
        @retrofit2.http.Query("page") page: Int = 2
    ): Response<ApiResponse>

    @GET(ENDPOINT_MOVIE_SINGLE)
    suspend fun getNewMovie(
        @retrofit2.http.Query("language") language: String = "en-US",
       // @retrofit2.http.Query("page") page: Int = 2
    ): Response<ApiResponse>


}