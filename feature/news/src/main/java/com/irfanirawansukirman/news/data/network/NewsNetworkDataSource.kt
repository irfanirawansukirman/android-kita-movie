package com.irfanirawansukirman.news.data.network

import com.irfanirawansukirman.core.ui.IOTaskResult
import com.irfanirawansukirman.news.data.contract.INewsRemoteDataSource
import com.irfanirawansukirman.news.data.contract.INewsWebService
import com.irfanirawansukirman.network.data.response.news.NewsResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsNetworkDataSource @Inject constructor(
    override val iWebService: INewsWebService
) : INewsRemoteDataSource {

    override suspend fun getNewsByTitle(param: HashMap<String, Any>): Flow<IOTaskResult<NewsResponse>> =
        iWebService.getNewsByTitle(param)

    override suspend fun getNewsByCategory(param: HashMap<String, Any>): Flow<IOTaskResult<NewsResponse>> =
        iWebService.getNewsByCategory(param)
}