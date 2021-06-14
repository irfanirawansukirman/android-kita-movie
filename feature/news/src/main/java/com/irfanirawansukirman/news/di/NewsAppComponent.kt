package com.irfanirawansukirman.news.di

import android.app.Application
import com.irfanirawansukirman.news.presentation.news.NewsActivity
import com.irfanirawansukirman.news.presentation.newscategories.BusinessFragment
import com.irfanirawansukirman.news.presentation.newscategories.HealthFragment
import com.irfanirawansukirman.news.presentation.newscategories.SportFragment
import com.irfanirawansukirman.news.presentation.newscategories.TechnologyFragment
import dagger.BindsInstance
import dagger.Component
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Singleton
@Component(modules = [NewsAppModule::class, NewsVMModule::class])
interface NewsAppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): NewsAppComponent
    }

    fun inject(activity: NewsActivity)
    fun inject(fragment: SportFragment)
    fun inject(fragment: TechnologyFragment)
    fun inject(fragment: HealthFragment)
    fun inject(fragment: BusinessFragment)
}