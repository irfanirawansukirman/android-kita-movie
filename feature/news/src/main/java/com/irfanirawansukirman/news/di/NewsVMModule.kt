package com.irfanirawansukirman.news.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.irfanirawansukirman.core.util.viewmodel.ViewModelFactory
import com.irfanirawansukirman.core.util.viewmodel.ViewModelKey
import com.irfanirawansukirman.news.presentation.news.NewsVM
import com.irfanirawansukirman.news.presentation.newscategories.NewsCategoryVM
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Module
abstract class NewsVMModule {

    @Binds
    internal abstract fun bindViewModelFactory(
        viewModelFactory: ViewModelFactory
    ): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(NewsVM::class)
    internal abstract fun bindNewsVM(viewModel: NewsVM): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NewsCategoryVM::class)
    internal abstract fun bindNewsCategoryVM(viewModel: NewsCategoryVM): ViewModel
}