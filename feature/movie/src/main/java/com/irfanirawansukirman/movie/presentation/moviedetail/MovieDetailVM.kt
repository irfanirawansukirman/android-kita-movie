package com.irfanirawansukirman.movie.presentation.moviedetail

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.irfanirawansukirman.core.base.BaseVM
import com.irfanirawansukirman.core.ui.UIState
import com.irfanirawansukirman.core.util.coroutine.CoroutineContextProvider
import com.irfanirawansukirman.movie.data.mapper.MovieWrapper
import com.irfanirawansukirman.movie.data.mapper.filterReviews
import com.irfanirawansukirman.movie.data.mapper.toUI
import com.irfanirawansukirman.movie.domain.MovieUseCaseImpl
import com.irfanirawansukirman.network.entity.MovieEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class MovieDetailVM @Inject constructor(
    context: Application,
    coroutineContextProvider: CoroutineContextProvider,
    private val movieUseCaseImpl: MovieUseCaseImpl
) : BaseVM(context, coroutineContextProvider) {

    private val _movie = MutableLiveData<UIState<MovieWrapper>>()
    val movie: LiveData<UIState<MovieWrapper>>
        get() = _movie

    private val _insertMovie = MutableLiveData<UIState<Int>>()
    val insertMovie: LiveData<UIState<Int>>
        get() = _insertMovie

    private val _movieById = MutableLiveData<MovieEntity>()
    val movieById: LiveData<MovieEntity>
        get() = _movieById

    fun getMovie(param: HashMap<String, Any>) {
        executeJob {
            _movie.value = UIState.Loading(true)
            try {
                val movie = movieUseCaseImpl.getMovie(param)
                val reviews = movieUseCaseImpl.getReviews(param)
                val data = MovieWrapper(movie.toUI(), reviews.filterReviews())
                _movie.value = UIState.Success(data)
            } catch (e: Exception) {
                _movie.value = UIState.Failure(e)
            }
            _movie.value = UIState.Loading(false)
        }
    }

    fun insertMovie(movieEntity: MovieEntity) {
        executeJob {
            _insertMovie.value = UIState.Loading(true)
            try {
                movieUseCaseImpl.insertMovie(movieEntity)
                _insertMovie.value = UIState.Success(1)
            } catch (e: Exception) {
                _insertMovie.value = UIState.Failure(e)
            }
            _insertMovie.value = UIState.Loading(false)
        }
    }

    fun getMovieById(movieId: Int) {
        executeJob {
            try {
                val data = movieUseCaseImpl.getMovieById(movieId)
                _movieById.value = data
            } catch (e: Exception) {
                // do with exception
            }
        }
    }
}