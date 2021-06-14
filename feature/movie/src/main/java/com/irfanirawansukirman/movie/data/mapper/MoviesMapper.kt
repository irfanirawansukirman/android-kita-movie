package com.irfanirawansukirman.movie.data.mapper

import com.irfanirawansukirman.network.data.response.movies.MoviesGeneralData
import com.irfanirawansukirman.network.data.response.movies.MoviesRangeData
import com.irfanirawansukirman.network.entity.MovieEntity

data class MoviesUI(
    val id: Int?,
    val posterPath: String?,
    val title: String?,
    val release: String?,
    val overview: String?
)

fun MoviesGeneralData.toUI(): MoviesUI {
    return MoviesUI(id, posterPath, originalTitle, releaseDate, overview)
}

fun MoviesRangeData.toUI(): MoviesUI {
    return MoviesUI(id, posterPath, originalTitle, releaseDate, overview)
}

fun MovieEntity.toUI(): MoviesUI {
    return MoviesUI(id, posterPath, name, release, overview)
}

