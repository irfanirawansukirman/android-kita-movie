package com.irfanirawansukirman.network.data.response.news

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class NewsResponse(
  @Json(name = "articles")
  val articles: List<ArticleData>?,
  @Json(name = "status")
  val status: String?,
  @Json(name = "totalResults")
  val totalResults: Int?
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class ArticleData(
  @Json(name = "author")
  val author: String?,
  @Json(name = "content")
  val content: String?,
  @Json(name = "description")
  val description: String?,
  @Json(name = "publishedAt")
  val publishedAt: String?,
  @Json(name = "source")
  val source: Source?,
  @Json(name = "title")
  val title: String?,
  @Json(name = "url")
  val url: String?,
  @Json(name = "urlToImage")
  val urlToImage: String?
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class Source(
  @Json(name = "id")
  val id: String?,
  @Json(name = "name")
  val name: String?
) : Parcelable