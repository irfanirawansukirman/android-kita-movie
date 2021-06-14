package com.irfanirawansukirman.movie.data.service

import com.irfanirawansukirman.core.ui.IOTaskResult
import com.irfanirawansukirman.core.util.coroutine.CoroutineContextProvider
import com.irfanirawansukirman.movie.data.contract.IMovieWebService
import com.irfanirawansukirman.network.data.response.movies.MovieResponse
import com.irfanirawansukirman.network.data.response.movies.MoviesGeneralResponse
import com.irfanirawansukirman.network.data.response.movies.MoviesRangeResponse
import com.irfanirawansukirman.network.data.response.movies.ReviewsResponse
import com.irfanirawansukirman.network.data.service.MovieService
import com.irfanirawansukirman.network.util.performSafeNetworkApiCall
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Singleton
class MovieServiceImpl @Inject constructor(
    private val coroutineContextProvider: CoroutineContextProvider,
    private val apiService: MovieService
) : IMovieWebService {

    override suspend fun getMoviesPopular(param: HashMap<String, Any>): Flow<IOTaskResult<MoviesGeneralResponse>> =
        performSafeNetworkApiCall(coroutineContextProvider) {
            apiService.getMoviesPopular(param)
        }

    override suspend fun getMovie(param: HashMap<String, Any>): MovieResponse {
        return withContext(coroutineContextProvider.io) {
            val movieId = param["movie_id"].toString().toInt()
            val apiKey = param["api_key"].toString()
            apiService.getMovie(movieId, apiKey)
        }
    }

    override suspend fun getMoviesUpcoming(param: HashMap<String, Any>): Flow<IOTaskResult<MoviesRangeResponse>> =
        performSafeNetworkApiCall(coroutineContextProvider) {
            apiService.getMoviesUpcoming(param)
        }

    override suspend fun getMoviesTopRated(param: HashMap<String, Any>): Flow<IOTaskResult<MoviesGeneralResponse>> =
        performSafeNetworkApiCall(coroutineContextProvider) {
            apiService.getMoviesTopRated(param)
        }

    override suspend fun getMoviesNowPlaying(param: HashMap<String, Any>): Flow<IOTaskResult<MoviesRangeResponse>> =
        performSafeNetworkApiCall(coroutineContextProvider) {
            apiService.getMoviesNowPlaying(param)
        }

    override suspend fun getReviews(param: HashMap<String, Any>): ReviewsResponse {
        return withContext(coroutineContextProvider.io) {
            val movieId = param["movie_id"].toString().toInt()
            val apiKey = param["api_key"].toString()
            apiService.getReviews(movieId, apiKey)
        }
    }
}