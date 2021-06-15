package com.irfanirawansukirman.movie.feature

import androidx.lifecycle.Observer
import com.irfanirawansukirman.core.ui.UIState
import com.irfanirawansukirman.movie.data.mapper.MoviesUI
import com.irfanirawansukirman.movie.domain.MovieUseCaseImpl
import com.irfanirawansukirman.movie.presentation.moviesfavorite.MoviesFavoriteVM
import com.irfanirawansukirman.movie.util.BaseTest
import com.irfanirawansukirman.network.entity.MovieEntity
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verifyOrder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class MoviesFavoriteVMTest : BaseTest() {

    @RelaxedMockK
    private lateinit var moviesObserver: Observer<UIState<List<MoviesUI>>>

    @RelaxedMockK
    private lateinit var movieUseCaseImpl: MovieUseCaseImpl

    private val moviesFavoriteResponse = listOf(
        MovieEntity(
            0,
            "",
            "",
            "",
            "",
            ""
        )
    )
    private val expectedMovies = listOf(
        MoviesUI(
            0,
            "",
            "",
            "",
            ""
        )
    )

    private val viewModel: MoviesFavoriteVM by lazy {
        MoviesFavoriteVM(context, testCoroutineContextProvider, movieUseCaseImpl)
    }

    @Test
    fun `get movies favorite is successfully`() = coroutinesRule.runBlockingTest {
        coEvery { movieUseCaseImpl.getAllFavoriteMovies() } returns moviesFavoriteResponse

        viewModel.movies.observeForever(moviesObserver)
        viewModel.getAllMoviesFavorite()

        verifyOrder {
            moviesObserver.onChanged(UIState.Loading(true))
            moviesObserver.onChanged(UIState.Success(expectedMovies))
            moviesObserver.onChanged(UIState.Loading(false))
        }
    }
}