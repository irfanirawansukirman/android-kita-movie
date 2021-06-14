package com.irfanirawansukirman.news.presentation.newscategories

import android.os.Bundle
import androidx.activity.viewModels
import com.irfanirawansukirman.core.base.BaseActivity
import com.irfanirawansukirman.news.databinding.NewsCategoriesActivityBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class NewsCategoriesActivity : BaseActivity() {

    private lateinit var newsCategoriesPagerAdapter: NewsCategoriesPagerAdapter

    // init for sharing vm to fragments
    private val viewModel by viewModels<NewsCategoryVM> { viewModelFactory }

    private var viewBinding: NewsCategoriesActivityBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewBinding()
        setContentView(viewBinding?.root)
        initNewsPager()
    }

    private fun initNewsPager() {
        val fragments = listOf(
            BusinessFragment.newInstance(),
            HealthFragment.newInstance(),
            SportFragment.newInstance(),
            TechnologyFragment.newInstance()
        )
        newsCategoriesPagerAdapter = NewsCategoriesPagerAdapter(supportFragmentManager, fragments)
        viewBinding?.vpNews?.apply {
            offscreenPageLimit = 4
            adapter = newsCategoriesPagerAdapter
            currentItem = 0
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        clearViewBinding()
    }

    private fun clearViewBinding() {
        viewBinding = null
    }

    private fun initViewBinding() {
        if (viewBinding == null) {
            viewBinding = NewsCategoriesActivityBinding.inflate(layoutInflater)
        }
    }
}