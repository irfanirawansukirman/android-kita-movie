package com.irfanirawansukirman.movie.feature

import androidx.lifecycle.Observer
import com.irfanirawansukirman.core.ui.IOTaskResult
import com.irfanirawansukirman.core.ui.UIState
import com.irfanirawansukirman.core.util.Params.API_KEY
import com.irfanirawansukirman.movie.BuildConfig
import com.irfanirawansukirman.movie.data.mapper.MoviesUI
import com.irfanirawansukirman.movie.domain.MovieUseCaseImpl
import com.irfanirawansukirman.movie.presentation.movies.MoviesVM
import com.irfanirawansukirman.movie.util.BaseTest
import com.irfanirawansukirman.movie.util.createMap
import com.irfanirawansukirman.network.data.response.movies.*
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verifyOrder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

@ExperimentalCoroutinesApi
class MoviesVMTest : BaseTest() {

    @RelaxedMockK
    private lateinit var moviesObserver: Observer<UIState<List<MoviesUI>>>

    @RelaxedMockK
    private lateinit var movieUseCaseImpl: MovieUseCaseImpl

    private val viewModel: MoviesVM by lazy {
        MoviesVM(context, testCoroutineContextProvider, movieUseCaseImpl)
    }

    private val param = createMap { put(API_KEY, BuildConfig.MOVIE_API_KEY) }

    @Test
    fun `get movies popular is successfully`() = coroutinesRule.runBlockingTest {
        coEvery { movieUseCaseImpl.getMoviesPopular(param) } returns fakeMoviesGeneralSuccessFlow

        viewModel.movies.observeForever(moviesObserver)
        viewModel.getMoviesPopular(param)

        verifyOrder {
            moviesObserver.onChanged(UIState.Loading(true))
            moviesObserver.onChanged(UIState.Success(expectedMovies))
            moviesObserver.onChanged(UIState.Loading(false))
        }
    }

    @Test
    fun `get movies popular is failed`() = coroutinesRule.runBlockingTest {
        coEvery { movieUseCaseImpl.getMoviesPopular(param) } returns fakeFailureFlow

        viewModel.movies.observeForever(moviesObserver)
        viewModel.getMoviesPopular(param)

        verifyOrder {
            moviesObserver.onChanged(UIState.Loading(true))
            moviesObserver.onChanged(UIState.Failure(mockException))
            moviesObserver.onChanged(UIState.Loading(false))
        }
    }

    @Test
    fun `get movies upcoming is successfully`() = coroutinesRule.runBlockingTest {
        coEvery { movieUseCaseImpl.getMoviesUpcoming(param) } returns fakeMoviesRangeSuccessFlow

        viewModel.movies.observeForever(moviesObserver)
        viewModel.getMoviesUpcoming(param)

        verifyOrder {
            moviesObserver.onChanged(UIState.Loading(true))
            moviesObserver.onChanged(UIState.Success(expectedMovies))
            moviesObserver.onChanged(UIState.Loading(false))
        }
    }

    @Test
    fun `get movies upcoming is failed`() = coroutinesRule.runBlockingTest {
        coEvery { movieUseCaseImpl.getMoviesUpcoming(param) } returns fakeFailureFlow

        viewModel.movies.observeForever(moviesObserver)
        viewModel.getMoviesUpcoming(param)

        verifyOrder {
            moviesObserver.onChanged(UIState.Loading(true))
            moviesObserver.onChanged(UIState.Failure(mockException))
            moviesObserver.onChanged(UIState.Loading(false))
        }
    }

    @Test
    fun `get movies top rated is successfully`() = coroutinesRule.runBlockingTest {
        coEvery { movieUseCaseImpl.getMoviesTopRated(param) } returns fakeMoviesGeneralSuccessFlow

        viewModel.movies.observeForever(moviesObserver)
        viewModel.getMoviesTopRated(param)

        verifyOrder {
            moviesObserver.onChanged(UIState.Loading(true))
            moviesObserver.onChanged(UIState.Success(expectedMovies))
            moviesObserver.onChanged(UIState.Loading(false))
        }
    }

    @Test
    fun `get movies top rated is failed`() = coroutinesRule.runBlockingTest {
        coEvery { movieUseCaseImpl.getMoviesTopRated(param) } returns fakeFailureFlow

        viewModel.movies.observeForever(moviesObserver)
        viewModel.getMoviesTopRated(param)

        verifyOrder {
            moviesObserver.onChanged(UIState.Loading(true))
            moviesObserver.onChanged(UIState.Failure(mockException))
            moviesObserver.onChanged(UIState.Loading(false))
        }
    }

    @Test
    fun `get movies now playing is successfully`() = coroutinesRule.runBlockingTest {
        coEvery { movieUseCaseImpl.getMoviesNowPlaying(param) } returns fakeMoviesRangeSuccessFlow

        viewModel.movies.observeForever(moviesObserver)
        viewModel.getMoviesNowPlaying(param)

        verifyOrder {
            moviesObserver.onChanged(UIState.Loading(true))
            moviesObserver.onChanged(UIState.Success(expectedMovies))
            moviesObserver.onChanged(UIState.Loading(false))
        }
    }

    @Test
    fun `get movies now playing is failed`() = coroutinesRule.runBlockingTest {
        coEvery { movieUseCaseImpl.getMoviesNowPlaying(param) } returns fakeFailureFlow

        viewModel.movies.observeForever(moviesObserver)
        viewModel.getMoviesNowPlaying(param)

        verifyOrder {
            moviesObserver.onChanged(UIState.Loading(true))
            moviesObserver.onChanged(UIState.Failure(mockException))
            moviesObserver.onChanged(UIState.Loading(false))
        }
    }

    override fun clear() {
        viewModel.movies.removeObserver(moviesObserver)
    }
}
