package com.irfanirawansukirman.movie.data.repository

import com.irfanirawansukirman.core.ui.IOTaskResult
import com.irfanirawansukirman.movie.data.contract.IMovieCacheDataSource
import com.irfanirawansukirman.movie.data.contract.IMovieRemoteDataSource
import com.irfanirawansukirman.movie.data.contract.IMovieRepository
import com.irfanirawansukirman.network.data.response.movies.MovieResponse
import com.irfanirawansukirman.network.data.response.movies.MoviesGeneralResponse
import com.irfanirawansukirman.network.data.response.movies.MoviesRangeResponse
import com.irfanirawansukirman.network.data.response.movies.ReviewsResponse
import com.irfanirawansukirman.network.entity.MovieEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepositoryImpl @Inject constructor(
    override val iRemoteDataSource: IMovieRemoteDataSource,
    override val iCacheDataSource: IMovieCacheDataSource
) : IMovieRepository {

    override suspend fun getMoviesPopular(param: HashMap<String, Any>): Flow<IOTaskResult<MoviesGeneralResponse>> =
        iRemoteDataSource.getMoviesPopular(param)

    override suspend fun getMovie(param: HashMap<String, Any>): MovieResponse =
        iRemoteDataSource.getMovie(param)

    override suspend fun getMoviesUpcoming(param: HashMap<String, Any>): Flow<IOTaskResult<MoviesRangeResponse>> =
        iRemoteDataSource.getMoviesUpcoming(param)

    override suspend fun getMoviesTopRated(param: HashMap<String, Any>): Flow<IOTaskResult<MoviesGeneralResponse>> =
        iRemoteDataSource.getMoviesTopRated(param)

    override suspend fun getMoviesNowPlaying(param: HashMap<String, Any>): Flow<IOTaskResult<MoviesRangeResponse>> =
        iRemoteDataSource.getMoviesNowPlaying(param)

    override suspend fun getReviews(param: HashMap<String, Any>): ReviewsResponse =
        iRemoteDataSource.getReviews(param)

    override suspend fun insertMovie(movieEntity: MovieEntity) {
        iCacheDataSource.insertMovie(movieEntity)
    }

    override suspend fun getMovieById(movieId: Int): MovieEntity =
        iCacheDataSource.getMovieById(movieId)

    override suspend fun getAllFavoriteMovies(): List<MovieEntity> =
        iCacheDataSource.getAllFavoriteMovies()
}