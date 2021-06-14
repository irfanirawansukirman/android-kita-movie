package com.irfanirawansukirman.movie.domain

import com.irfanirawansukirman.core.ui.IOTaskResult
import com.irfanirawansukirman.movie.data.contract.IMovieRepository
import com.irfanirawansukirman.movie.data.contract.IMovieUseCase
import com.irfanirawansukirman.network.data.response.movies.MovieResponse
import com.irfanirawansukirman.network.data.response.movies.MoviesGeneralResponse
import com.irfanirawansukirman.network.data.response.movies.MoviesRangeResponse
import com.irfanirawansukirman.network.data.response.movies.ReviewsResponse
import com.irfanirawansukirman.network.entity.MovieEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieUseCaseImpl @Inject constructor(private val iRepository: IMovieRepository) :
    IMovieUseCase.Popular<HashMap<String, Any>, MoviesGeneralResponse>,
    IMovieUseCase.Movie<HashMap<String, Any>, MovieResponse>,
    IMovieUseCase.Upcoming<HashMap<String, Any>, MoviesRangeResponse>,
    IMovieUseCase.TopRated<HashMap<String, Any>, MoviesGeneralResponse>,
    IMovieUseCase.NowPlaying<HashMap<String, Any>, MoviesRangeResponse>,
    IMovieUseCase.Reviews<HashMap<String, Any>, ReviewsResponse>,
    IMovieUseCase.InsertMovie<MovieEntity>,
    IMovieUseCase.MovieById<Int, MovieEntity>,
    IMovieUseCase.AllMoviesFavorite<List<MovieEntity>> {

    override suspend fun getMoviesPopular(param: HashMap<String, Any>): Flow<IOTaskResult<MoviesGeneralResponse>> =
        iRepository.getMoviesPopular(param)

    override suspend fun getMovie(param: HashMap<String, Any>): MovieResponse =
        iRepository.getMovie(param)

    override suspend fun getMoviesUpcoming(param: HashMap<String, Any>): Flow<IOTaskResult<MoviesRangeResponse>> =
        iRepository.getMoviesUpcoming(param)

    override suspend fun getMoviesTopRated(param: HashMap<String, Any>): Flow<IOTaskResult<MoviesGeneralResponse>> =
        iRepository.getMoviesTopRated(param)

    override suspend fun getMoviesNowPlaying(param: HashMap<String, Any>): Flow<IOTaskResult<MoviesRangeResponse>> =
        iRepository.getMoviesNowPlaying(param)

    override suspend fun getReviews(param: HashMap<String, Any>): ReviewsResponse =
        iRepository.getReviews(param)

    override suspend fun insertMovie(movieEntity: MovieEntity) {
        iRepository.insertMovie(movieEntity)
    }

    override suspend fun getMovieById(param: Int): MovieEntity = iRepository.getMovieById(param)

    override suspend fun getAllFavoriteMovies(): List<MovieEntity> =
        iRepository.getAllFavoriteMovies()
}