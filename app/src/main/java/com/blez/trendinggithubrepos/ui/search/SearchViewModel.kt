package com.blez.trendinggithubrepos.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.blez.trendinggithubrepos.data.Article
import com.blez.trendinggithubrepos.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val newsRepository: NewsRepository)  : ViewModel() {
    private fun getSearchData(query : String) : Flow<PagingData<Article>> = newsRepository.getSearchNewsPaging(query).flow.cachedIn(viewModelScope)
    fun list(query: String) : Flow<PagingData<Article>>  = getSearchData(query)

}