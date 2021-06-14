package com.irfanirawansukirman.movie.data.mapper

import com.irfanirawansukirman.network.data.response.movies.MovieResponse
import com.irfanirawansukirman.network.data.response.movies.ReviewsData
import com.irfanirawansukirman.network.data.response.movies.ReviewsResponse

data class MovieUI(
    val title: String?,
    val release: String?,
    val poster: String?,
    val rating: Double?,
    val genres: MutableList<String?>?,
    val overview: String?
)

fun MovieResponse.filterGenres(): MutableList<String?> {
    return mutableListOf<String?>().apply {
        genres?.forEach {
            add(it.name)
        }
    }
}

fun MovieResponse.toUI(): MovieUI {
    return MovieUI(
        title,
        releaseDate,
        posterPath,
        voteAverage,
        filterGenres(),
        overview
    )
}

data class ReviewsUI(
    val avatar: String?,
    val name: String?,
    val rating: Int?,
    val content: String?,
    val url: String?
)

fun ReviewsResponse.filterReviews(): MutableList<ReviewsUI?> {
    return mutableListOf<ReviewsUI?>().apply { results?.forEach { add(it.toUI()) } }
}

fun ReviewsData.toUI(): ReviewsUI {
    return ReviewsUI(authorDetails?.avatarPath, author, authorDetails?.rating, content, url)
}

data class MovieWrapper(
    val movie: MovieUI?,
    val review: MutableList<ReviewsUI?>
)