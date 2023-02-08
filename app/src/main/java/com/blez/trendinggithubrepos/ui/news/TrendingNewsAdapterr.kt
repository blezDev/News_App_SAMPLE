package com.blez.trendinggithubrepos.ui.news

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.blez.trendinggithubrepos.R
import com.blez.trendinggithubrepos.data.Article
import com.blez.trendinggithubrepos.databinding.NewsViewBinding
import com.bumptech.glide.Glide
import javax.inject.Inject

class TrendingNewsAdapter @Inject constructor (val context : Context): PagingDataAdapter<Article,TrendingNewsAdapter.ItemViewHolder>(
    COMPARATOR) {
    private lateinit var binding: NewsViewBinding
    var onItemClick : ((Article)->Unit) ?= null

    inner class ItemViewHolder(binding: NewsViewBinding) : RecyclerView.ViewHolder(binding.root)






    companion object{
        private val COMPARATOR = object : DiffUtil.ItemCallback<Article>(){
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.urlToImage == newItem.urlToImage
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.setIsRecyclable(false)

        val item = getItem(position)
        item?.let {item->
            Glide.with(context).load(item.urlToImage).into(binding.newImage).onLoadFailed(AppCompatResources.getDrawable(context,R.drawable.load_error))
            binding.titleText.text = item.title
            binding.desText.text = item.description
          binding.readMoreBTN.setOnClickListener {
              onItemClick?.invoke(item)
          }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
      binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.news_view,parent,false)
        return ItemViewHolder(binding)
    }
}