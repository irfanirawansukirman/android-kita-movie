package com.irfanirawansukirman.movie.di

import android.app.Application
import com.irfanirawansukirman.movie.presentation.moviedetail.MovieDetailActivity
import com.irfanirawansukirman.movie.presentation.movies.MoviesActivity
import com.irfanirawansukirman.movie.presentation.movies.MoviesCategoryDialog
import com.irfanirawansukirman.movie.presentation.moviesfavorite.MoviesFavoriteActivity
import dagger.BindsInstance
import dagger.Component
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Named
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Singleton
@Component(modules = [MovieAppModule::class, MovieVMModule::class])
interface MovieAppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): MovieAppComponent
    }

    fun inject(activity: MoviesActivity)

    fun inject(dialog: MoviesCategoryDialog)

    fun inject(activity: MovieDetailActivity)

    fun inject(activity: MoviesFavoriteActivity)
}