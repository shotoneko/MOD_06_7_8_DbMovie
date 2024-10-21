package modulo_06.ej06.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import modulo_06.ej06.retrofit.RestDataSource
import modulo_06.ej06.room.DbDataSource
import modulo_06.ej06.room.DbMovieDao
import modulo_06.ej06.utils.Constants.Companion.BASE_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {
    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val ENDPOINT_NAME = "?inc=name"
        const val ENDPOINT_PICTURE = "?inc=picture"
        const val ENDPOINT_LOCATION = "?inc=location"
    }

    @Singleton
    @Provides
    @Named("BaseUrl")
    fun provideBaseUrl() = BASE_URL

    @Singleton
    @Provides
    fun provideRetrofit(@Named("BaseUrl") baseUrl: String, okHttpClient: OkHttpClient): Retrofit {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(OkHttpClient.Builder()
                .addInterceptor(Interceptor { chain ->
                    val request = chain.request().newBuilder()
                        .addHeader("accept", "application/json")
                        .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI5NDg4MDZhMjg3NjFlMjM0MWJjYzllZmI1NDcxZDkxOSIsIm5iZiI6MTcyOTE3NjAyOC42NzIyMjgsInN1YiI6IjY3MTExYmNmMWI5MTJhZGQyZWRiZmE1NiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.-JJdc17OH1cANFv3eeopTQw_NLhCOydnBreVB_aKBqI")
                        .build()
                    chain.proceed(request)
                })
                .build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder().build()

    @Singleton
    @Provides
    fun restDataSource(retrofit: Retrofit): RestDataSource =
        retrofit.create(RestDataSource::class.java)

    @Singleton
    @Provides
    fun dbDataSource(@ApplicationContext context: Context): DbDataSource {
        return Room.databaseBuilder(
            context,
            DbDataSource::class.java,
            "movies_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun dbDao(db: DbDataSource): DbMovieDao = db.moviesDao()
}