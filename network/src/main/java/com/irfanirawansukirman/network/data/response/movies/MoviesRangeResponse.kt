package com.irfanirawansukirman.network.data.response.movies

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class MoviesRangeResponse(
    @Json(name = "dates")
    val dates: MoviesRangeDates?,
    @Json(name = "page")
    val page: Int?,
    @Json(name = "results")
    val results: List<MoviesRangeData>?,
    @Json(name = "total_pages")
    val totalPages: Int?,
    @Json(name = "total_results")
    val totalResults: Int?
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class MoviesRangeDates(
    @Json(name = "maximum")
    val maximum: String?,
    @Json(name = "minimum")
    val minimum: String?
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class MoviesRangeData(
    @Json(name = "adult")
    val adult: Boolean?,
    @Json(name = "backdrop_path")
    val backdropPath: String?,
    @Json(name = "genre_ids")
    val genreIds: List<Int>?,
    @Json(name = "id")
    val id: Int?,
    @Json(name = "original_language")
    val originalLanguage: String?,
    @Json(name = "original_title")
    val originalTitle: String?,
    @Json(name = "overview")
    val overview: String?,
    @Json(name = "popularity")
    val popularity: Double?,
    @Json(name = "poster_path")
    val posterPath: String?,
    @Json(name = "release_date")
    val releaseDate: String?,
    @Json(name = "title")
    val title: String?,
    @Json(name = "video")
    val video: Boolean?,
    @Json(name = "vote_average")
    val voteAverage: Double?,
    @Json(name = "vote_count")
    val voteCount: Int?
) : Parcelable