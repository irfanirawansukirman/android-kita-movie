package com.irfanirawansukirman.news.presentation.newscategories

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.irfanirawansukirman.core.base.BaseFragmentNoVM
import com.irfanirawansukirman.core.ui.UIState
import com.irfanirawansukirman.core.util.extension.*
import com.irfanirawansukirman.news.BuildConfig
import com.irfanirawansukirman.news.data.mapper.NewsUI
import com.irfanirawansukirman.news.databinding.NewsItemBinding
import com.irfanirawansukirman.news.databinding.TechnologyFragmentBinding
import com.irfanirawansukirman.news.di.NewsComponentProvider
import com.irfanirawansukirman.news.presentation.news.NewsItemHolder
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class TechnologyFragment :
    BaseFragmentNoVM<TechnologyFragmentBinding>(TechnologyFragmentBinding::inflate) {

    private val viewModel by activityViewModels<NewsCategoryVM> { viewModelFactory }

    private val newsAdapter by lazy {
        viewBinding.rvNews.generateVerticalAdapter<
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

    private var isPrepared = false
    private var isHasLoadedOnce = false

    override fun initInjector() {
        (getApplication() as NewsComponentProvider)
            .getNewsComponent()
            .inject(this)
    }

    override fun initComponent() {
        // empty state
    }

    override fun initViewListener() {
        viewBinding.progress.executeWhenStartProgress { getNewsByCategory() }
    }

    override fun onViewReady(savedInstanceState: Bundle?, view: View) {
        isPrepared = true

        loadVM()
    }

    override fun lazyLoad() {
        if (!isPrepared || !isUIVisible || isHasLoadedOnce){
            return
        }
        getNewsByCategory()
    }

    override fun onClear() {
        // empty state
    }

    private fun loadVM() {
        viewModel.news.subscribe(viewLifecycleOwner, ::showNews)
    }

    private fun getNewsByCategory() {
        isHasLoadedOnce = true

        val param = HashMap<String, Any>().apply {
            put("apiKey", BuildConfig.NEWS_API_KEY)
            put("country", "id")
            put("category", "technology")
        }
        viewModel.getNewsByCategory(param)
    }

    private fun showNews(state: UIState<List<NewsUI>>) {
        when (state) {
            is UIState.Loading -> if (state.isLoading) viewBinding.progress.play()
            is UIState.Success -> {
                viewBinding.progress.stop()

                newsAdapter.submitList(state.output)
            }
            is UIState.Failure -> {
                // do with error
            }
        }
    }

    companion object {
        fun newInstance() = TechnologyFragment().putArgs { }
    }
}