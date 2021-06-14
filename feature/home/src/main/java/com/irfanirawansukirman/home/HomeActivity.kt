package com.irfanirawansukirman.home

import android.content.Context
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import br.com.simplepass.loadingbutton.customViews.CircularProgressButton
import com.irfanirawansukirman.core.util.Features.MOVIES
import com.irfanirawansukirman.core.util.Features.NEWS_CATEGORIES
import com.irfanirawansukirman.core.util.Times.THREE_POINT_FIVE_MILLIS
import com.irfanirawansukirman.core.util.extension.navigationForPackage
import com.irfanirawansukirman.home.databinding.HomeActivityBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.irfanirawansukirman.core.R as R2

class HomeActivity : AppCompatActivity() {

    private var viewBinding: HomeActivityBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewBinding()
        setContentView(viewBinding?.root)

        viewBinding?.apply {
            btnMovies.apply { setOnClickListener { navigateToPage(this, true) } }
            btnNews.apply { setOnClickListener { navigateToPage(this) } }
        }
    }

    private fun navigateToPage(
        circularProgressButton: CircularProgressButton,
        isToMovies: Boolean = false
    ) {
        circularProgressButton.apply {
            startAnimation {
                lifecycleScope.launch {
                    delay(THREE_POINT_FIVE_MILLIS)
                    doneLoadingAnimation(
                        defaultColor(context),
                        defaultDoneImage(context.resources, isToMovies)
                    )
                    delay(THREE_POINT_FIVE_MILLIS)
                    revertAnimation()
                    delay(THREE_POINT_FIVE_MILLIS)
                    if (isToMovies) navigateToMovies() else navigateToNewsCategory()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        clearViewBinding()
    }

    private fun clearViewBinding() {
        viewBinding?.apply { btnMovies.dispose(); btnNews.dispose() }
        viewBinding = null
    }

    private fun initViewBinding() {
        if (viewBinding == null) {
            viewBinding = HomeActivityBinding.inflate(layoutInflater)
        }
    }

    private fun defaultColor(context: Context) =
        ContextCompat.getColor(context, R2.color.colorAccent)

    private fun defaultDoneImage(resources: Resources, isMovie: Boolean = false) =
        BitmapFactory.decodeResource(resources, getIconByType(isMovie))

    private fun getIconByType(isMovie: Boolean): Int {
        return if (isMovie) R2.drawable.ic_movie_fill else R2.drawable.ic_news_fill
    }

    private fun navigateToMovies() {
        navigationForPackage(MOVIES) {}
    }

    private fun navigateToNewsCategory() {
        navigationForPackage(NEWS_CATEGORIES) {}
    }
}