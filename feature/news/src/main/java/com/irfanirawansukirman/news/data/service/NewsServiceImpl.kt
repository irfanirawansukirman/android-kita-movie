package com.irfanirawansukirman.news.data.service

import com.irfanirawansukirman.core.ui.IOTaskResult
import com.irfanirawansukirman.core.util.coroutine.CoroutineContextProvider
import com.irfanirawansukirman.network.data.response.news.NewsResponse
import com.irfanirawansukirman.network.data.service.NewsService
import com.irfanirawansukirman.network.util.performSafeNetworkApiCall
import com.irfanirawansukirman.news.data.contract.INewsWebService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Singleton
class NewsServiceImpl @Inject constructor(
    private val coroutineContextProvider: CoroutineContextProvider,
    private val apiService: NewsService
) : INewsWebService {

    override suspend fun getNewsByTitle(param: HashMap<String, Any>): Flow<IOTaskResult<NewsResponse>> =
        performSafeNetworkApiCall(coroutineContextProvider) {
            apiService.getNewsEverything(param)
        }

    override suspend fun getNewsByCategory(param: HashMap<String, Any>): Flow<IOTaskResult<NewsResponse>> =
        performSafeNetworkApiCall(coroutineContextProvider) {
            apiService.getNewsByCategory(param)
        }
}