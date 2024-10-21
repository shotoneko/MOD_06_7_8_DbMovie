package modulo_06.ej06.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import modulo_06.ej06.repository.Repository
import modulo_06.ej06.repository.RepositoryImp
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun repository(repositoryImp: RepositoryImp): Repository
}