package com.irfanirawansukirman.news.data.contract

import com.irfanirawansukirman.core.ui.IOTaskResult
import com.irfanirawansukirman.network.data.response.news.NewsResponse
import kotlinx.coroutines.flow.Flow

interface INewsRemoteDataSource {

    val iWebService: INewsWebService

    suspend fun getNewsByTitle(param: HashMap<String, Any>): Flow<IOTaskResult<NewsResponse>>
    suspend fun getNewsByCategory(param: HashMap<String, Any>): Flow<IOTaskResult<NewsResponse>>
}

interface INewsRepository {

    val iRemoteDataSource: INewsRemoteDataSource

    suspend fun getNewsByTitle(param: HashMap<String, Any>): Flow<IOTaskResult<NewsResponse>>
    suspend fun getNewsByCategory(param: HashMap<String, Any>): Flow<IOTaskResult<NewsResponse>>
}

interface INewsUseCase {

    interface Everything<in I : Any, out O : Any> {

        suspend fun getNewsByTitle(param: I): Flow<IOTaskResult<O>>
    }

    interface NewsByCategory<in I : Any, out O : Any> {

        suspend fun getNewsByCategory(param: I): Flow<IOTaskResult<O>>
    }
}

interface INewsWebService {

    suspend fun getNewsByTitle(param: HashMap<String, Any>): Flow<IOTaskResult<NewsResponse>>
    suspend fun getNewsByCategory(param: HashMap<String, Any>): Flow<IOTaskResult<NewsResponse>>
}