package com.irfanirawansukirman.news.domain

import com.irfanirawansukirman.core.ui.IOTaskResult
import com.irfanirawansukirman.network.data.response.news.NewsResponse
import com.irfanirawansukirman.news.data.contract.INewsRepository
import com.irfanirawansukirman.news.data.contract.INewsUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsUseCaseImpl @Inject constructor(private val iRepository: INewsRepository) :
    INewsUseCase.Everything<HashMap<String, Any>, NewsResponse>,
    INewsUseCase.NewsByCategory<HashMap<String, Any>, NewsResponse> {

    override suspend fun getNewsByTitle(param: HashMap<String, Any>): Flow<IOTaskResult<NewsResponse>> =
        iRepository.getNewsByTitle(param)

    override suspend fun getNewsByCategory(param: HashMap<String, Any>): Flow<IOTaskResult<NewsResponse>> =
        iRepository.getNewsByCategory(param)
}