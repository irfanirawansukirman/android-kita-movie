package com.irfanirawansukirman.news.presentation.newscategories

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class NewsCategoriesPagerAdapter(fm: FragmentManager, private val fragments: List<Fragment>) :
    FragmentStatePagerAdapter(fm) {

    override fun getCount(): Int = fragments.size

    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Business"
            1 -> "Health"
            2 -> "Sport"
            else -> "Tech"
        }
    }
}