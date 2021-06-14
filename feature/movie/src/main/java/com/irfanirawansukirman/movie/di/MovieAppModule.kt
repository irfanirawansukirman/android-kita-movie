package com.irfanirawansukirman.movie.di

import com.irfanirawansukirman.core.di.BaseModule
import com.irfanirawansukirman.core.util.coroutine.CoroutineContextProvider
import com.irfanirawansukirman.movie.data.cache.MovieCacheDataSource
import com.irfanirawansukirman.movie.data.contract.IMovieCacheDataSource
import com.irfanirawansukirman.movie.data.contract.IMovieRemoteDataSource
import com.irfanirawansukirman.movie.data.contract.IMovieRepository
import com.irfanirawansukirman.movie.data.contract.IMovieWebService
import com.irfanirawansukirman.movie.data.network.MovieNetworkDataSource
import com.irfanirawansukirman.movie.data.repository.MovieRepositoryImpl
import com.irfanirawansukirman.movie.data.service.MovieServiceImpl
import com.irfanirawansukirman.movie.domain.MovieUseCaseImpl
import com.irfanirawansukirman.network.dao.MovieDao
import com.irfanirawansukirman.network.data.service.MovieService
import com.irfanirawansukirman.network.di.CacheModule
import com.irfanirawansukirman.network.di.NetworkModule
import com.irfanirawansukirman.network.factory.ApiFactory
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.ExperimentalCoroutinesApi
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Module(includes = [BaseModule::class, NetworkModule::class, CacheModule::class])
class MovieAppModule {

    // source: https://stackoverflow.com/a/35949468/7550660
    @Singleton
    @Provides
    fun provideMovieService(@Named("movie_url") retrofit: Retrofit): MovieService = ApiFactory.getService(retrofit)

    @Provides
    fun provideMovieWebService(
        coroutineContextProvider: CoroutineContextProvider,
        movieService: MovieService
    ): IMovieWebService = MovieServiceImpl(coroutineContextProvider, movieService)

    @Provides
    fun provideMovieNetworkDataSource(iMovieWebService: IMovieWebService): IMovieRemoteDataSource =
        MovieNetworkDataSource(iMovieWebService)

    @Provides
    fun provideMovieCacheDataSource(movieDao: MovieDao): IMovieCacheDataSource =
        MovieCacheDataSource(movieDao)

    @Provides
    fun provideMovieRepositoryImpl(
        networkDataSource: MovieNetworkDataSource,
        cacheDataSource: MovieCacheDataSource
    ): IMovieRepository = MovieRepositoryImpl(networkDataSource, cacheDataSource)

    @Provides
    fun provideMovieUseCaseImpl(iMovieRepository: IMovieRepository): MovieUseCaseImpl =
        MovieUseCaseImpl(iMovieRepository)
}