package com.irfanirawansukirman.movie.feature

import androidx.lifecycle.Observer
import com.irfanirawansukirman.core.ui.IOTaskResult
import com.irfanirawansukirman.core.ui.UIState
import com.irfanirawansukirman.movie.BuildConfig
import com.irfanirawansukirman.movie.data.mapper.MoviesUI
import com.irfanirawansukirman.movie.domain.MovieUseCaseImpl
import com.irfanirawansukirman.movie.presentation.movies.MoviesVM
import com.irfanirawansukirman.movie.util.BaseTest
import com.irfanirawansukirman.movie.util.createMap
import com.irfanirawansukirman.network.data.response.movies.*
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verifyOrder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class MoviesVMTest : BaseTest() {

    @RelaxedMockK
    private lateinit var moviesObserver: Observer<UIState<List<MoviesUI>>>

    @RelaxedMockK
    private lateinit var movieUseCaseImpl: MovieUseCaseImpl

    private val fakeMoviesGeneralSuccessFlow = flow {
        emit(
            IOTaskResult.OnSuccess(
                MoviesGeneralResponse(
                    0,
                    listOf(
                        MoviesGeneralData(
                            false,
                            "",
                            listOf(1, 2, 3),
                            0,
                            "",
                            "",
                            "",
                            0.0,
                            "",
                            "",
                            "",
                            false,
                            0.0,
                            0
                        )
                    ),
                    0,
                    0
                )
            )
        )
    }

    private val fakeMoviesRangeSuccessFlow = flow {
        emit(
            IOTaskResult.OnSuccess(
                MoviesRangeResponse(
                    MoviesRangeDates("", ""),
                    0,
                    listOf(
                        MoviesRangeData(
                            false,
                            "",
                            listOf(1, 2, 3),
                            0,
                            "",
                            "",
                            "",
                            0.0,
                            "",
                            "",
                            "",
                            false,
                            0.0,
                            0
                        )
                    ),
                    0,
                    0
                )
            )
        )
    }

    private val expectedMovies = listOf(
        MoviesUI(
            0,
            "",
            "",
            "",
            ""
        )
    )

    private val viewModel: MoviesVM by lazy {
        MoviesVM(context, testCoroutineContextProvider, movieUseCaseImpl)
    }

    @Before
    fun `setup depends`() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `get movies popular is successfully`() = coroutinesRule.runBlockingTest {
        val param = createMap { put("api_key", BuildConfig.MOVIE_API_KEY) }
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
    fun `get movies upcoming is successfully`() = coroutinesRule.runBlockingTest {
        val param = createMap { put("api_key", BuildConfig.MOVIE_API_KEY) }
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
    fun `get movies top rated is successfully`() = coroutinesRule.runBlockingTest {
        val param = createMap { put("api_key", BuildConfig.MOVIE_API_KEY) }
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
    fun `get movies now playing is successfully`() = coroutinesRule.runBlockingTest {
        val param = createMap { put("api_key", BuildConfig.MOVIE_API_KEY) }
        coEvery { movieUseCaseImpl.getMoviesNowPlaying(param) } returns fakeMoviesRangeSuccessFlow

        viewModel.movies.observeForever(moviesObserver)
        viewModel.getMoviesNowPlaying(param)

        verifyOrder {
            moviesObserver.onChanged(UIState.Loading(true))
            moviesObserver.onChanged(UIState.Success(expectedMovies))
            moviesObserver.onChanged(UIState.Loading(false))
        }
    }

    @After
    fun `clear all`() {
        clearAllMocks()
    }
}

