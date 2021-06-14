package com.irfanirawansukirman.core.base

import androidx.appcompat.app.AppCompatActivity
import com.irfanirawansukirman.core.util.viewmodel.ViewModelFactory
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
}