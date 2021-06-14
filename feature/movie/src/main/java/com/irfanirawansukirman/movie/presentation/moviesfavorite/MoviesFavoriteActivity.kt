package com.irfanirawansukirman.movie.presentation.moviesfavorite

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.irfanirawansukirman.core.databinding.ToolbarDefaultBinding
import com.irfanirawansukirman.core.ui.UIState
import com.irfanirawansukirman.core.ui.UIState.*
import com.irfanirawansukirman.core.util.extension.generateVerticalAdapter
import com.irfanirawansukirman.core.util.extension.hideProgress
import com.irfanirawansukirman.core.util.extension.showProgress
import com.irfanirawansukirman.core.util.extension.subscribe
import com.irfanirawansukirman.core.util.viewmodel.ViewModelFactory
import com.irfanirawansukirman.movie.R
import com.irfanirawansukirman.movie.data.mapper.MoviesUI
import com.irfanirawansukirman.movie.databinding.MoviesFavoriteActivityBinding
import com.irfanirawansukirman.movie.databinding.MoviesItemBinding
import com.irfanirawansukirman.movie.di.MovieComponentProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class MoviesFavoriteActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by viewModels<MoviesFavoriteVM> { viewModelFactory }

    private val moviesAdapter by lazy {
        viewBinding?.rvMovies?.generateVerticalAdapter<
                MoviesUI,
                MoviesFavoriteItemHolder,
                MoviesItemBinding
                >(
            viewHolder = ::MoviesFavoriteItemHolder,
            bindingInflater = MoviesItemBinding::inflate,
            hasFixedSize = true,
            reverseLayout = false,
            binder = { item, holder, _, _ -> holder.bindItem(item) {} }
        )
    }

    private var viewBinding: MoviesFavoriteActivityBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initInjector()
        initViewBinding()
        setContentView(viewBinding?.root)
        initToolbar()
        loadVM()

        getAllMoviesFavorite()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewBinding = null
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }
        else -> false
    }

    private fun initInjector() {
        (application as MovieComponentProvider)
            .getMovieComponent()
            .inject(this)
    }

    private fun initViewBinding() {
        if (viewBinding == null) {
            viewBinding = MoviesFavoriteActivityBinding.inflate(layoutInflater)
        }
    }

    private fun initToolbar() {
        setSupportActionBar(getToolbarViewBinding()?.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = getString(R.string.title_favorite_movie)
        }
    }

    private fun getToolbarViewBinding() = viewBinding?.root?.let { ToolbarDefaultBinding.bind(it) }

    private fun loadVM() {
        viewModel.movies.subscribe(this, ::showMovies)
    }

    private fun getAllMoviesFavorite() {
        viewModel.getAllMoviesFavorite()
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
}