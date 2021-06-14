package com.irfanirawansukirman.movie.data.contract

import com.irfanirawansukirman.core.ui.IOTaskResult
import com.irfanirawansukirman.network.data.response.movies.MovieResponse
import com.irfanirawansukirman.network.data.response.movies.MoviesGeneralResponse
import com.irfanirawansukirman.network.data.response.movies.MoviesRangeResponse
import com.irfanirawansukirman.network.data.response.movies.ReviewsResponse
import com.irfanirawansukirman.network.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

interface IMovieRemoteDataSource {

    val iWebService: IMovieWebService

    suspend fun getMoviesPopular(param: HashMap<String, Any>): Flow<IOTaskResult<MoviesGeneralResponse>>

    suspend fun getMovie(param: HashMap<String, Any>): MovieResponse

    suspend fun getMoviesUpcoming(param: HashMap<String, Any>): Flow<IOTaskResult<MoviesRangeResponse>>

    suspend fun getMoviesTopRated(param: HashMap<String, Any>): Flow<IOTaskResult<MoviesGeneralResponse>>

    suspend fun getMoviesNowPlaying(param: HashMap<String, Any>): Flow<IOTaskResult<MoviesRangeResponse>>

    suspend fun getReviews(param: HashMap<String, Any>): ReviewsResponse
}

interface IMovieCacheDataSource {

    suspend fun insertMovie(movieEntity: MovieEntity)

    suspend fun getMovieById(movieId: Int): MovieEntity

    suspend fun getAllFavoriteMovies(): List<MovieEntity>
}

interface IMovieRepository {

    val iRemoteDataSource: IMovieRemoteDataSource
    val iCacheDataSource: IMovieCacheDataSource

    suspend fun getMoviesPopular(param: HashMap<String, Any>): Flow<IOTaskResult<MoviesGeneralResponse>>

    suspend fun getMovie(param: HashMap<String, Any>): MovieResponse

    suspend fun getMoviesUpcoming(param: HashMap<String, Any>): Flow<IOTaskResult<MoviesRangeResponse>>

    suspend fun getMoviesTopRated(param: HashMap<String, Any>): Flow<IOTaskResult<MoviesGeneralResponse>>

    suspend fun getMoviesNowPlaying(param: HashMap<String, Any>): Flow<IOTaskResult<MoviesRangeResponse>>

    suspend fun getReviews(param: HashMap<String, Any>): ReviewsResponse

    suspend fun insertMovie(movieEntity: MovieEntity)

    suspend fun getMovieById(movieId: Int): MovieEntity

    suspend fun getAllFavoriteMovies(): List<MovieEntity>
}

interface IMovieUseCase {

    interface Popular<in I : Any, out O : Any> {

        suspend fun getMoviesPopular(param: I): Flow<IOTaskResult<O>>
    }

    interface Movie<in I : Any, out O : Any> {

        suspend fun getMovie(param: I): O
    }

    interface Upcoming<in I : Any, out O : Any> {

        suspend fun getMoviesUpcoming(param: I): Flow<IOTaskResult<O>>
    }

    interface TopRated<in I : Any, out O : Any> {

        suspend fun getMoviesTopRated(param: I): Flow<IOTaskResult<O>>
    }

    interface NowPlaying<in I : Any, out O : Any> {

        suspend fun getMoviesNowPlaying(param: I): Flow<IOTaskResult<O>>
    }

    interface Reviews<in I : Any, out O : Any> {

        suspend fun getReviews(param: I): O
    }

    interface InsertMovie<in I : Any> {

        suspend fun insertMovie(movieEntity: I)
    }

    interface MovieById<in I : Any, out O : Any> {

        suspend fun getMovieById(param: I): O
    }

    interface AllMoviesFavorite<out O : Any> {

        suspend fun getAllFavoriteMovies(): O
    }
}

interface IMovieWebService {

    suspend fun getMoviesPopular(param: HashMap<String, Any>): Flow<IOTaskResult<MoviesGeneralResponse>>

    suspend fun getMovie(param: HashMap<String, Any>): MovieResponse

    suspend fun getMoviesUpcoming(param: HashMap<String, Any>): Flow<IOTaskResult<MoviesRangeResponse>>

    suspend fun getMoviesTopRated(param: HashMap<String, Any>): Flow<IOTaskResult<MoviesGeneralResponse>>

    suspend fun getMoviesNowPlaying(param: HashMap<String, Any>): Flow<IOTaskResult<MoviesRangeResponse>>

    suspend fun getReviews(param: HashMap<String, Any>): ReviewsResponse
}