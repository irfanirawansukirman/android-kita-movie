package com.irfanirawansukirman.network.data.response.movies

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize


@Parcelize
@JsonClass(generateAdapter = true)
data class ReviewsResponse(
    @Json(name = "id")
    val id: Int?,
    @Json(name = "page")
    val page: Int?,
    @Json(name = "results")
    val results: List<ReviewsData>?,
    @Json(name = "total_pages")
    val totalPages: Int?,
    @Json(name = "total_results")
    val totalResults: Int?
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class ReviewsData(
    @Json(name = "author")
    val author: String?,
    @Json(name = "author_details")
    val authorDetails: AuthorDetails?,
    @Json(name = "content")
    val content: String?,
    @Json(name = "created_at")
    val createdAt: String?,
    @Json(name = "id")
    val id: String?,
    @Json(name = "updated_at")
    val updatedAt: String?,
    @Json(name = "url")
    val url: String?
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class AuthorDetails(
    @Json(name = "avatar_path")
    val avatarPath: String?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "rating")
    val rating: Int?,
    @Json(name = "username")
    val username: String?
) : Parcelable