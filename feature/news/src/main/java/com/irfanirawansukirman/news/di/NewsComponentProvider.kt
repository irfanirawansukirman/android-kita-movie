package com.irfanirawansukirman.news.di

import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
interface NewsComponentProvider {

    fun getNewsComponent(): NewsAppComponent
}