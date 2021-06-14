package com.irfanirawansukirman.news.data.mapper

import com.irfanirawansukirman.network.data.response.news.ArticleData

data class NewsUI(
    val title: String?,
    val poster: String?,
    val author: String?,
    val published: String?
)

fun ArticleData.toUI() = NewsUI(title, urlToImage, author, publishedAt)