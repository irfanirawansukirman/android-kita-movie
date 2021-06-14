package com.irfanirawansukirman.news.di

import com.irfanirawansukirman.core.di.BaseModule
import com.irfanirawansukirman.core.util.coroutine.CoroutineContextProvider
import com.irfanirawansukirman.network.data.service.NewsService
import com.irfanirawansukirman.network.di.NetworkModule
import com.irfanirawansukirman.network.factory.ApiFactory
import com.irfanirawansukirman.news.data.contract.INewsRemoteDataSource
import com.irfanirawansukirman.news.data.contract.INewsRepository
import com.irfanirawansukirman.news.data.contract.INewsWebService
import com.irfanirawansukirman.news.data.network.NewsNetworkDataSource
import com.irfanirawansukirman.news.data.repository.NewsRepositoryImpl
import com.irfanirawansukirman.news.data.service.NewsServiceImpl
import com.irfanirawansukirman.news.domain.NewsUseCaseImpl
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.ExperimentalCoroutinesApi
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Module(includes = [BaseModule::class, NetworkModule::class])
class NewsAppModule {

    @Singleton
    @Provides
    fun provideMovieService(@Named("news_url") retrofit: Retrofit): NewsService = ApiFactory.getService(retrofit)

    @Provides
    fun provideNewsWebService(
        coroutineContextProvider: CoroutineContextProvider,
        newsService: NewsService
    ): INewsWebService = NewsServiceImpl(coroutineContextProvider, newsService)

    @Provides
    fun provideNewsNetworkDataSource(
        iNewsWebService: INewsWebService
    ): INewsRemoteDataSource = NewsNetworkDataSource(iNewsWebService)

    @Provides
    fun provideNewsRepositoryImpl(
        networkDataSource: NewsNetworkDataSource,
    ): INewsRepository = NewsRepositoryImpl(networkDataSource)

    @Provides
    fun provideNewsUseCaseImpl(iNewsRepository: INewsRepository): NewsUseCaseImpl =
        NewsUseCaseImpl(iNewsRepository)
}