package com.irfanirawansukirman.movie.presentation.movies

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import com.irfanirawansukirman.core.base.BaseActivity
import com.irfanirawansukirman.core.databinding.ToolbarDefaultBinding
import com.irfanirawansukirman.core.ui.UIState
import com.irfanirawansukirman.core.ui.UIState.*
import com.irfanirawansukirman.core.util.Params.API_KEY
import com.irfanirawansukirman.core.util.Params.MOVIE_ID
import com.irfanirawansukirman.core.util.extension.*
import com.irfanirawansukirman.movie.BuildConfig
import com.irfanirawansukirman.movie.R
import com.irfanirawansukirman.movie.data.mapper.MoviesUI
import com.irfanirawansukirman.movie.databinding.MoviesActivityBinding
import com.irfanirawansukirman.movie.databinding.MoviesItemBinding
import com.irfanirawansukirman.movie.di.MovieComponentProvider
import com.irfanirawansukirman.movie.presentation.moviedetail.MovieDetailActivity
import com.irfanirawansukirman.movie.presentation.movies.MoviesCategoryState.*
import com.irfanirawansukirman.movie.presentation.moviesfavorite.MoviesFavoriteActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MoviesActivity : BaseActivity() {

    private var viewBinding: MoviesActivityBinding? = null

    private val viewModel by viewModels<MoviesVM> { viewModelFactory }

    private val moviesAdapter by lazy {
        viewBinding?.rvMovies?.generateVerticalAdapter<
                MoviesUI,
                MoviesItemHolder,
                MoviesItemBinding
                >(
            viewHolder = ::MoviesItemHolder,
            bindingInflater = MoviesItemBinding::inflate,
            hasFixedSize = true,
            reverseLayout = false,
            binder = { item, holder, _, _ ->
                holder.bindItem(item) { movie -> navigateToMovieDetail(movie.id) }
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initInjector()
        initProgressDialog()
        initViewBinding()
        setContentView(viewBinding?.root)
        initToolbar()
        initViewListener()
        loadVM()

        getMoviesPopular()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewBinding = null
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.movie, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.actionFavorite -> {
            navigateToMovieFavorite()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun getToolbarViewBinding() = viewBinding?.root?.let { ToolbarDefaultBinding.bind(it) }

    private fun initToolbar() {
        setSupportActionBar(getToolbarViewBinding()?.toolbar)
        getToolbarViewBinding()?.toolbar?.title = getString(R.string.app_name)
    }

    private fun initViewListener() {
        viewBinding?.tvCategory?.setOnClickListener { showCategoryDialog() }
    }

    private fun showCategoryDialog() {
        val dialog = MoviesCategoryDialog.newInstance()
        dialog.show(supportFragmentManager, dialog.tag)
    }

    private fun initInjector() {
        (application as MovieComponentProvider)
            .getMovieComponent()
            .inject(this)
    }

    private fun initViewBinding() {
        if (viewBinding == null) {
            viewBinding = MoviesActivityBinding.inflate(layoutInflater)
        }
    }

    private fun loadVM() {
        viewModel.apply {
            movies.subscribe(this@MoviesActivity, ::showMovies)
            categoryState.subscribe(this@MoviesActivity, ::getMoviesByCategory)
        }
    }

    private fun getMoviesByCategory(state: MoviesCategoryState) {
        when (state) {
            is Popular -> getMoviesPopular()
            is Upcoming -> getMoviesUpcoming()
            is TopRated -> getMoviesTopRated()
            is NowPlaying -> getMoviesNowPlaying()
        }
    }

    private fun getMoviesPopular() {
        val param = HashMap<String, Any>().apply { put(API_KEY, BuildConfig.MOVIE_API_KEY) }
        viewModel.getMoviesPopular(param)
    }

    private fun getMoviesUpcoming() {
        val param = HashMap<String, Any>().apply { put(API_KEY, BuildConfig.MOVIE_API_KEY) }
        viewModel.getMoviesUpcoming(param)
    }

    private fun getMoviesTopRated() {
        val param = HashMap<String, Any>().apply { put(API_KEY, BuildConfig.MOVIE_API_KEY) }
        viewModel.getMoviesTopRated(param)
    }

    private fun getMoviesNowPlaying() {
        val param = HashMap<String, Any>().apply { put(API_KEY, BuildConfig.MOVIE_API_KEY) }
        viewModel.getMoviesNowPlaying(param)
    }

    private fun showMovies(state: UIState<List<MoviesUI>>) {
        when (state) {
            is Loading -> if (state.isLoading) showProgress(supportFragmentManager)
            is Success -> {
                hideProgress()

                moviesAdapter?.submitList(state.output)
            }
            is Failure -> {
                // do with error
            }
        }
    }

    private fun navigateToMovieDetail(movieId: Int?) {
        navigation<MovieDetailActivity> { putExtra(MOVIE_ID, movieId) }
    }

    private fun navigateToMovieFavorite() {
        navigation<MoviesFavoriteActivity>()
    }
}
