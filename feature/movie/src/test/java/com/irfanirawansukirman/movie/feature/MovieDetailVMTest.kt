package com.irfanirawansukirman.movie.feature

import androidx.lifecycle.Observer
import com.irfanirawansukirman.core.ui.UIState
import com.irfanirawansukirman.movie.data.mapper.MovieUI
import com.irfanirawansukirman.movie.data.mapper.MovieWrapper
import com.irfanirawansukirman.movie.data.mapper.ReviewsUI
import com.irfanirawansukirman.movie.domain.MovieUseCaseImpl
import com.irfanirawansukirman.movie.presentation.moviedetail.MovieDetailVM
import com.irfanirawansukirman.movie.util.BaseTest
import com.irfanirawansukirman.movie.util.createMap
import com.irfanirawansukirman.network.data.response.movies.*
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
class MovieDetailVMTest : BaseTest() {

    @RelaxedMockK
    private lateinit var movieObserver: Observer<UIState<MovieWrapper>>

    @RelaxedMockK
    private lateinit var movieUseCaseImpl: MovieUseCaseImpl

    private val viewModel: MovieDetailVM by lazy {
        MovieDetailVM(context, testCoroutineContextProvider, movieUseCaseImpl)
    }

    @Test
    fun `get movie detail with review is successfully`() = coroutinesRule.runBlockingTest {
        val param = createMap { }
        val movieResponse = MovieResponse(
            false,
            "",
            BelongsToCollection("", 0, "", ""),
            0,
            listOf(Genre(0, "")),
            "",
            0,
            "",
            "",
            "",
            "",
            0.0,
            "",
            listOf(ProductionCompany(0, "", "", "")),
            listOf(ProductionCountry("", "")),
            "",
            0,
            0,
            listOf(SpokenLanguage("", "", "")),
            "",
            "",
            "",
            false,
            0.0,
            0
        )
        val reviewsResponse = ReviewsResponse(
            0,
            0,
            listOf(
                ReviewsData(
                    "",
                    AuthorDetails("", "", 0, ""),
                    "",
                    "",
                    "",
                    "",
                    ""
                )
            ),
            0,
            0
        )

        coEvery { movieUseCaseImpl.getMovie(param) } returns movieResponse
        coEvery { movieUseCaseImpl.getReviews(param) } returns reviewsResponse

        viewModel.movie.observeForever(movieObserver)
        viewModel.getMovie(param)

        val movieUI = MovieUI(
            "",
            "",
            "",
            0.0,
            mutableListOf(""),
            ""
        )
        val reviewsUI = mutableListOf(
            ReviewsUI(
                "",
                "",
                0,
                "",
                ""
            )
        )
        val expectedMovie = MovieWrapper(movieUI, reviewsUI.toMutableList())

        verifyOrder {
            movieObserver.onChanged(UIState.Loading(true))
            movieObserver.onChanged(UIState.Success(expectedMovie))
            movieObserver.onChanged(UIState.Loading(false))
        }
    }
}