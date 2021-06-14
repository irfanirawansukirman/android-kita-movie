package com.irfanirawansukirman.news.data.repository

import com.irfanirawansukirman.core.ui.IOTaskResult
import com.irfanirawansukirman.network.data.response.news.NewsResponse
import com.irfanirawansukirman.news.data.contract.INewsRemoteDataSource
import com.irfanirawansukirman.news.data.contract.INewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepositoryImpl @Inject constructor(
    override val iRemoteDataSource: INewsRemoteDataSource
) : INewsRepository {

    override suspend fun getNewsByTitle(param: HashMap<String, Any>): Flow<IOTaskResult<NewsResponse>> =
        iRemoteDataSource.getNewsByTitle(param)

    override suspend fun getNewsByCategory(param: HashMap<String, Any>): Flow<IOTaskResult<NewsResponse>> =
        iRemoteDataSource.getNewsByCategory(param)
}