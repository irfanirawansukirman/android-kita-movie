package com.irfanirawansukirman.network.data.service

import com.irfanirawansukirman.network.data.response.news.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface NewsService {

    @GET("everything")
    suspend fun getNewsEverything(
        @QueryMap param: HashMap<String, Any>
    ): Response<NewsResponse>

    @GET("top-headlines")
    suspend fun getNewsByCategory(
        @QueryMap param: HashMap<String, Any>
    ): Response<NewsResponse>
}