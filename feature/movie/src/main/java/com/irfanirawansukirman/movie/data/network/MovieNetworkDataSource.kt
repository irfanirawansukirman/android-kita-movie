package com.irfanirawansukirman.movie.data.network

import com.irfanirawansukirman.core.ui.IOTaskResult
import com.irfanirawansukirman.movie.data.contract.IMovieRemoteDataSource
import com.irfanirawansukirman.movie.data.contract.IMovieWebService
import com.irfanirawansukirman.network.data.response.movies.MovieResponse
import com.irfanirawansukirman.network.data.response.movies.MoviesGeneralResponse
import com.irfanirawansukirman.network.data.response.movies.MoviesRangeResponse
import com.irfanirawansukirman.network.data.response.movies.ReviewsResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieNetworkDataSource @Inject constructor(override val iWebService: IMovieWebService) :
    IMovieRemoteDataSource {

    override suspend fun getMoviesPopular(param: HashMap<String, Any>): Flow<IOTaskResult<MoviesGeneralResponse>> =
        iWebService.getMoviesPopular(param)

    override suspend fun getMovie(param: HashMap<String, Any>): MovieResponse =
        iWebService.getMovie(param)

    override suspend fun getMoviesUpcoming(param: HashMap<String, Any>): Flow<IOTaskResult<MoviesRangeResponse>> =
        iWebService.getMoviesUpcoming(param)

    override suspend fun getMoviesTopRated(param: HashMap<String, Any>): Flow<IOTaskResult<MoviesGeneralResponse>> =
        iWebService.getMoviesTopRated(param)

    override suspend fun getMoviesNowPlaying(param: HashMap<String, Any>): Flow<IOTaskResult<MoviesRangeResponse>> =
        iWebService.getMoviesNowPlaying(param)

    override suspend fun getReviews(param: HashMap<String, Any>): ReviewsResponse =
        iWebService.getReviews(param)
}