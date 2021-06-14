package com.irfanirawansukirman.news.presentation.news

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.irfanirawansukirman.core.util.extension.orDefault
import com.irfanirawansukirman.news.data.mapper.NewsUI
import com.irfanirawansukirman.news.databinding.NewsItemBinding

class NewsItemHolder(private val binding: NewsItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindItem(news: NewsUI) {
        binding.apply {
            Log.d("Irfan ", news.title.orDefault("Tak ada judul"))
            ivPoster.loadImageUrl(news.poster.orDefault(""))
            tvTitle.text = news.title
            tvPubslished.text = news.published
        }
    }
}