package com.irfanirawansukirman.movie.util

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.harukaedu.pintaria.util.MainCoroutinesRule
import com.irfanirawansukirman.core.ui.IOTaskResult
import com.irfanirawansukirman.core.util.coroutine.TestCoroutineContextProvider
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import org.junit.After
import org.junit.Before
import org.junit.Rule

@ExperimentalCoroutinesApi
abstract class BaseTest {

    @get:Rule
    var coroutinesRule = MainCoroutinesRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    val testCoroutineContextProvider = TestCoroutineContextProvider()

    @RelaxedMockK
    lateinit var mockException: Exception

    @RelaxedMockK
    lateinit var context: Application

    val fakeFailureFlow = flow {
        emit(IOTaskResult.OnFailed(mockException))
    }

    @Before
    fun `setup depends`() {
        MockKAnnotations.init(this)
    }

    @After
    fun `clear all`() {
        clearAllMocks()
    }
}