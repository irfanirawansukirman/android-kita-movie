package com.irfanirawansukirman.network.di

import android.app.Application
import com.irfanirawansukirman.network.BuildConfig
import com.irfanirawansukirman.network.factory.ApiFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    @Named("movie_url")
    fun provideMovieRetrofit(application: Application): Retrofit =
        ApiFactory.build(
            application,
            BuildConfig.MOVIE_API_BASE_URL
        )

    @Singleton
    @Provides
    @Named("news_url")
    fun provideNewsRetrofit(application: Application): Retrofit =
        ApiFactory.build(
            application,
            BuildConfig.NEWS_API_BASE_URL_V2
        )
}