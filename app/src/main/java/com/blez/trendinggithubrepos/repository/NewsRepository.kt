package com.blez.trendinggithubrepos.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.blez.trendinggithubrepos.data.API.NewAPI
import com.blez.trendinggithubrepos.paging.SearchNewsPagingSource
import com.blez.trendinggithubrepos.paging.TrendingNewsPagingSource
import retrofit2.http.Query
import javax.inject.Inject

class NewsRepository @Inject constructor(val newsAPI: NewAPI) {

    fun getTrendingNewsPaging() = Pager(config = PagingConfig(pageSize = 20),
        pagingSourceFactory = { TrendingNewsPagingSource(newsAPI) })
    fun getSearchNewsPaging(query: String) = Pager(config = PagingConfig(pageSize = 20),
    pagingSourceFactory = {SearchNewsPagingSource(newsAPI,query)})

}