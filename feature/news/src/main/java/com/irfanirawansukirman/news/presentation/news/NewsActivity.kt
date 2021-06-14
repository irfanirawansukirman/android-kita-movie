package com.irfanirawansukirman.news.presentation.news

import android.os.Bundle
import androidx.activity.viewModels
import com.irfanirawansukirman.core.base.BaseActivity
import com.irfanirawansukirman.core.ui.UIState
import com.irfanirawansukirman.core.util.extension.*
import com.irfanirawansukirman.news.BuildConfig
import com.irfanirawansukirman.news.data.mapper.NewsUI
import com.irfanirawansukirman.news.databinding.NewsActivityBinding
import com.irfanirawansukirman.news.databinding.NewsItemBinding
import com.irfanirawansukirman.news.di.NewsComponentProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class NewsActivity : BaseActivity() {

    private var viewBinding: NewsActivityBinding? = null

    private val query by extra<String>("query")
    private val viewModel by viewModels<NewsVM> { viewModelFactory }

    private val newsAdapter by lazy {
        viewBinding?.rvNews?.generateVerticalAdapter<
                NewsUI,
                NewsItemHolder,
                NewsItemBinding
                >(
            isGrid = true,
            numOfColumns = 2,
            isIncludeEdge = true,
            viewHolder = ::NewsItemHolder,
            bindingInflater = NewsItemBinding::inflate,
            hasFixedSize = true,
            reverseLayout = false,
            binder = { item, holder, _, _ ->
                holder.bindItem(item)
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initInjector()
        initViewBinding()
        setContentView(viewBinding?.root)
        loadVM()

        getNewsEverything(query.orDefault(""))
    }

    override fun onDestroy() {
        super.onDestroy()
        clearViewBinding()
    }

    private fun clearViewBinding() {
        viewBinding = null
    }

    private fun initInjector() {
        (application as NewsComponentProvider)
            .getNewsComponent()
            .inject(this)
    }

    private fun initViewBinding() {
        if (viewBinding == null) {
            viewBinding = NewsActivityBinding.inflate(layoutInflater)
        }
    }

    private fun loadVM() {
        viewModel.apply {
            news.subscribe(this@NewsActivity, ::showNews)
        }
    }

    private fun getNewsEverything(query: String) {
        val param = HashMap<String, Any>().apply {
            put("apiKey", BuildConfig.NEWS_API_KEY)
            put("q", query)
        }
        viewModel.getNewsEverything(param)
    }

    private fun showNews(state: UIState<List<NewsUI>>) {
        when (state) {
            is UIState.Loading -> if (state.isLoading) showProgress(supportFragmentManager)
            is UIState.Success -> {
                hideProgress()

                newsAdapter?.submitList(state.output)
            }
            is UIState.Failure -> {
                // do with error
            }
        }
    }
}