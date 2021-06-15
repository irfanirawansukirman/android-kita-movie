package com.irfanirawansukirman.movie.feature

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.harukaedu.pintaria.util.MainCoroutinesRule
import com.irfanirawansukirman.core.util.Params.API_KEY
import com.irfanirawansukirman.movie.BuildConfig
import com.irfanirawansukirman.movie.data.contract.IMovieCacheDataSource
import com.irfanirawansukirman.movie.data.contract.IMovieRemoteDataSource
import com.irfanirawansukirman.movie.data.repository.MoviesRepositoryImpl
import com.irfanirawansukirman.movie.util.createMap
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Created by Irfan Irawan Sukirman on 15/06/21 at Bandung City
 * Copyright (c) 2021 PT. Haruka Evolusi Digital Utama. All rights reserved.
 */
@ExperimentalCoroutinesApi
class MoviesRepositoryTest {

    @get:Rule
    var coroutinesRule = MainCoroutinesRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @RelaxedMockK
    private lateinit var iRemoteDataSource: IMovieRemoteDataSource

    @RelaxedMockK
    private lateinit var iCacheDataSource: IMovieCacheDataSource

    private val repository: MoviesRepositoryImpl by lazy {
        MoviesRepositoryImpl(iRemoteDataSource, iCacheDataSource)
    }

    private val param = createMap { put(API_KEY, BuildConfig.MOVIE_API_KEY) }

    @Before
    fun `setup depends`() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `get movies popular is successfully`() = coroutinesRule.runBlockingTest {
        coEvery { iRemoteDataSource.getMoviesPopular(param) } returns
        repository.getMoviesPopular(param)

        coVerify(exactly = 1) { iRemoteDataSource.getMoviesPopular(param) }
    }

    @Test
    fun `get movies popular is failed`() = coroutinesRule.runBlockingTest {
        coVerify(exactly = 0) { iRemoteDataSource.getMoviesPopular(param) }
    }

    @Test
    fun `get movies upcoming is successfully`() = coroutinesRule.runBlockingTest {
        repository.getMoviesUpcoming(param)

        coVerify(exactly = 1) { iRemoteDataSource.getMoviesUpcoming(param) }
    }

    @Test
    fun `get movies upcoming is failed`() = coroutinesRule.runBlockingTest {
        coVerify(exactly = 0) { iRemoteDataSource.getMoviesUpcoming(param) }
    }

    @Test
    fun `get movies top rated is successfully`() = coroutinesRule.runBlockingTest {
        repository.getMoviesTopRated(param)

        coVerify(exactly = 1) { iRemoteDataSource.getMoviesTopRated(param) }
    }

    @Test
    fun `get movies top rated is failed`() = coroutinesRule.runBlockingTest {
        coVerify(exactly = 0) { iRemoteDataSource.getMoviesTopRated(param) }
    }

    @Test
    fun `get movies now playing is successfully`() = coroutinesRule.runBlockingTest {
        repository.getMoviesNowPlaying(param)

        coVerify(exactly = 1) { iRemoteDataSource.getMoviesNowPlaying(param) }
    }

    @Test
    fun `get movies now playing is failed`() = coroutinesRule.runBlockingTest {
        coVerify(exactly = 0) { iRemoteDataSource.getMoviesNowPlaying(param) }
    }

    @After
    fun `clear all`() {
        clearAllMocks()
    }
}