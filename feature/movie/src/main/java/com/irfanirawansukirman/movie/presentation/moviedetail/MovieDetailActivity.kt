package com.irfanirawansukirman.movie.presentation.moviedetail

import android.os.Bundle
import android.view.MenuItem
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.irfanirawansukirman.core.R
import com.irfanirawansukirman.core.base.BaseActivity
import com.irfanirawansukirman.core.databinding.ToolbarDefaultBinding
import com.irfanirawansukirman.core.ui.UIState
import com.irfanirawansukirman.core.ui.UIState.*
import com.irfanirawansukirman.core.util.Params.MOVIE_ID
import com.irfanirawansukirman.core.util.extension.*
import com.irfanirawansukirman.movie.BuildConfig
import com.irfanirawansukirman.movie.data.mapper.MovieUI
import com.irfanirawansukirman.movie.data.mapper.MovieWrapper
import com.irfanirawansukirman.movie.data.mapper.ReviewsUI
import com.irfanirawansukirman.movie.databinding.MovieDetailActivityBinding
import com.irfanirawansukirman.movie.databinding.ReviewItemBinding
import com.irfanirawansukirman.movie.di.MovieComponentProvider
import com.irfanirawansukirman.network.entity.MovieEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class MovieDetailActivity : BaseActivity() {

    private var viewBinding: MovieDetailActivityBinding? = null

    private val viewModel by viewModels<MovieDetailVM> { viewModelFactory }

    private val reviewsAdapter by lazy {
        viewBinding?.rvReviews?.generateHorizontalAdapter<ReviewsUI, ReviewViewHolder, ReviewItemBinding>(
            ::ReviewViewHolder,
            ReviewItemBinding::inflate,
            hasFixedSize = true,
            reverseLayout = false,
            binder = { item, holder, _, _ ->
                holder.bindItem(item) { navigationToBrowser(it.orDefault("https://google.com")) }
            }
        )
    }

    private var movieUI: MovieUI? = null
    private var isFavorite: Boolean? = null

    private val movieId by extra<Int>(MOVIE_ID)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initInjector()
        initViewBinding()
        setContentView(viewBinding?.root)
        initToolbar()
        initViewListener()
        loadVM()

        getMovie(movieId)
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

    private fun getToolbarViewBinding() = viewBinding?.root?.let { ToolbarDefaultBinding.bind(it) }

    private fun initInjector() {
        (application as MovieComponentProvider)
            .getMovieComponent()
            .inject(this)
    }

    private fun initViewBinding() {
        if (viewBinding == null) {
            viewBinding = MovieDetailActivityBinding.inflate(layoutInflater)
        }
    }

    private fun initToolbar() {
        setSupportActionBar(getToolbarViewBinding()?.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = null
        }
    }

    private fun initViewListener() {
        var isLiked = false
        viewBinding?.ivFavorite?.setOnClickListener {
            isLiked = !isLiked
            val movieEntity = MovieEntity(
                movieId.orDefault(0),
                movieUI?.title,
                movieUI?.poster,
                movieUI?.release,
                movieUI?.overview,
                if (isFavorite == false && (isLiked || !isLiked)) {
                    "t"
                } else {
                    "f"
                }
            )
            insertMovie(movieEntity)
        }
    }

    private fun loadVM() {
        viewModel.apply {
            movie.subscribe(this@MovieDetailActivity, ::showMovie)
            insertMovie.subscribe(this@MovieDetailActivity, ::cacheMovie)
            movieById.subscribe(this@MovieDetailActivity, ::initFavorite)
        }
    }

    private fun getMovie(movieId: Int?) {
        val param = HashMap<String, Any>().apply {
            put("movie_id", movieId.orDefault(0))
            put("api_key", BuildConfig.MOVIE_API_KEY)
        }
        viewModel.getMovie(param)
    }

    private fun showMovie(state: UIState<MovieWrapper>) {
        when (state) {
            is Loading -> if (state.isLoading) showProgress(supportFragmentManager)
            is Success -> {
                hideProgress()

                movieUI = state.output.movie
                supportActionBar?.title = movieUI?.title
                viewBinding?.apply {
                    ivPoster.loadImageUrl("${BuildConfig.MOVIE_POSTER_BASE_URL}${movieUI?.poster}")
                    tvTitle.text = movieUI?.title
                    tvDate.text = movieUI?.release
                    tvOverview.text = movieUI?.overview
                }

                val review = state.output.review
                reviewsAdapter?.submitList(review)

                getMovieById(movieId)
            }
            is Failure -> {
                // do with error
            }
        }
    }

    private fun cacheMovie(state: UIState<Int>) {
        when (state) {
            is Loading -> if (state.isLoading) showProgress(supportFragmentManager)
            is Success -> lifecycleScope.launch {
                delay(350)
                hideProgress()
                getMovieById(movieId)
            }
            is Failure -> {
                // do with error
            }
        }
    }

    private fun insertMovie(movieEntity: MovieEntity) {
        viewModel.insertMovie(movieEntity)
    }

    private fun getMovieById(movieId: Int?) {
        viewModel.getMovieById(movieId.orDefault(0))
    }

    private fun initFavorite(movieEntity: MovieEntity) {
        isFavorite = !(movieEntity.favorite == null || movieEntity.favorite == "f")

        viewBinding?.ivFavorite?.apply {
            setImageDrawable(
                getResDrawable(
                    this@MovieDetailActivity,
                    if (movieEntity.favorite == "t") R.drawable.ic_heart_fill else R.drawable.ic_heart_outline
                )
            )
            if (movieEntity.favorite == "t") {
                startAnimation(AnimationUtils.loadAnimation(this@MovieDetailActivity, R.anim.like))
            }
        }
    }
}
