package com.irfanirawansukirman.news.presentation.news

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.irfanirawansukirman.core.base.BaseVM
import com.irfanirawansukirman.core.ui.UIState
import com.irfanirawansukirman.core.util.coroutine.CoroutineContextProvider
import com.irfanirawansukirman.network.util.getViewStateFlowForNetworkCall
import com.irfanirawansukirman.news.data.mapper.NewsUI
import com.irfanirawansukirman.news.data.mapper.toUI
import com.irfanirawansukirman.news.domain.NewsUseCaseImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@ExperimentalCoroutinesApi
class NewsVM @Inject constructor(
    context: Application,
    private val coroutineContextProvider: CoroutineContextProvider,
    private val useCaseImpl: NewsUseCaseImpl
) : BaseVM(context, coroutineContextProvider) {

    private val _news = MutableLiveData<UIState<List<NewsUI>>>()
    val news: LiveData<UIState<List<NewsUI>>>
        get() = _news

    fun getNewsEverything(param: HashMap<String, Any>) {
        executeJob {
            getViewStateFlowForNetworkCall(coroutineContextProvider) {
                useCaseImpl.getNewsByTitle(param)
            }.collect {
                switchToMain {
                    when (it) {
                        is UIState.Loading -> _news.value = it
                        is UIState.Success -> {
                            val articles = it.output.articles
                            val dataUI = mutableListOf<NewsUI>().apply {
                                if (articles != null && articles.isNotEmpty()) {
                                    articles.forEach { item ->  add(item.toUI()) }
                                } else {
                                    addAll(emptyList())
                                }
                            }
                            _news.value = UIState.Success(dataUI)
                        }
                        is UIState.Failure -> _news.value = it
                    }
                }
            }
        }
    }
}