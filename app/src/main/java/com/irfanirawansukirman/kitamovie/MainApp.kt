package com.irfanirawansukirman.kitamovie

import android.app.Application
import com.facebook.stetho.Stetho
import com.irfanirawansukirman.movie.di.DaggerMovieAppComponent
import com.irfanirawansukirman.movie.di.MovieAppComponent
import com.irfanirawansukirman.movie.di.MovieComponentProvider
import com.irfanirawansukirman.news.di.DaggerNewsAppComponent
import com.irfanirawansukirman.news.di.NewsAppComponent
import com.irfanirawansukirman.news.di.NewsComponentProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MainApp : Application(),
    MovieComponentProvider,
    NewsComponentProvider {

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
    }

    override fun getMovieComponent(): MovieAppComponent {
        return DaggerMovieAppComponent
            .builder()
            .application(this)
            .build()
    }

    override fun getNewsComponent(): NewsAppComponent {
        return DaggerNewsAppComponent
            .builder()
            .application(this)
            .build()
    }
}