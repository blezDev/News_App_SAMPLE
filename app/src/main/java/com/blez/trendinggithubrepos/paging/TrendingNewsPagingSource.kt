package com.blez.trendinggithubrepos.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.blez.trendinggithubrepos.data.API.NewAPI
import com.blez.trendinggithubrepos.data.Article
import com.blez.trendinggithubrepos.utils.Constants
import com.blez.trendinggithubrepos.utils.Constants.API_KEY

class TrendingNewsPagingSource(private val newsAPI: NewAPI,private var country : String = "in")  : PagingSource<Int, Article>() {
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
     val position = params.key ?:1
        val response = newsAPI.tendingNews(country, API_KEY,position)
        return try {
            LoadResult.Page(
                data = response.articles,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (position ==2) null else position +1
            )
        }catch (e:Exception){
            LoadResult.Error(e)
        }
    }


}