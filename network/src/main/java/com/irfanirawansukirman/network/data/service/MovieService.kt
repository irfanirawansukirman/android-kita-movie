package com.irfanirawansukirman.network.data.service

import com.irfanirawansukirman.network.BuildConfig
import com.irfanirawansukirman.network.data.response.movies.MovieResponse
import com.irfanirawansukirman.network.data.response.movies.MoviesGeneralResponse
import com.irfanirawansukirman.network.data.response.movies.MoviesRangeResponse
import com.irfanirawansukirman.network.data.response.movies.ReviewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface MovieService {

    @GET("${BuildConfig.MOVIE_API_BASE_URL}movie/popular")
    suspend fun getMoviesPopular(
        @QueryMap param: HashMap<String, Any>
    ): Response<MoviesGeneralResponse>

    @GET("${BuildConfig.MOVIE_API_BASE_URL}movie/{movie_id}")
    suspend fun getMovie(
        @Path("movie_id") movieId: Int,
        @Query("api_key") param: String
    ): MovieResponse

    @GET("${BuildConfig.MOVIE_API_BASE_URL}movie/upcoming")
    suspend fun getMoviesUpcoming(
        @QueryMap param: HashMap<String, Any>
    ): Response<MoviesRangeResponse>

    @GET("${BuildConfig.MOVIE_API_BASE_URL}movie/top_rated")
    suspend fun getMoviesTopRated(
        @QueryMap param: HashMap<String, Any>
    ): Response<MoviesGeneralResponse>

    @GET("${BuildConfig.MOVIE_API_BASE_URL}movie/now_playing")
    suspend fun getMoviesNowPlaying(
        @QueryMap param: HashMap<String, Any>
    ): Response<MoviesRangeResponse>

    @GET("${BuildConfig.MOVIE_API_BASE_URL}movie/{movie_id}/reviews")
    suspend fun getReviews(
        @Path("movie_id") movieId: Int,
        @Query("api_key") param: String
    ): ReviewsResponse
}