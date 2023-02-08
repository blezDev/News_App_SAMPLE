package com.blez.trendinggithubrepos.ui.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.blez.trendinggithubrepos.data.Article
import com.blez.trendinggithubrepos.data.NewsData
import com.blez.trendinggithubrepos.data.SearchNewsData
import com.blez.trendinggithubrepos.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val newsRepository: NewsRepository) : ViewModel() {
     sealed class SetupEvent {
          data class trendNewsEvent(val data : Flow<PagingData<Article>>) : SetupEvent()

          object NoEventYet : SetupEvent()
          object trendNewsLoadingEvent : SetupEvent()

          object SearchNewsLoadingEvent : SetupEvent()

          data class SearchNewsEvent(val data : SearchNewsData) : SetupEvent()

     }
     private val _searchNews = MutableStateFlow<SetupEvent>(SetupEvent.NoEventYet)
     val searchNews : StateFlow<SetupEvent> = _searchNews


     private val _trendNews = MutableStateFlow<SetupEvent>(SetupEvent.NoEventYet)
     val trendNews : StateFlow<SetupEvent> = _trendNews


     val list = newsRepository.getTrendingNewsPaging().flow.cachedIn(viewModelScope)

     private fun getSearchData(query : String) : Flow<PagingData<Article>> = newsRepository.getSearchNewsPaging(query).flow.cachedIn(viewModelScope)
     fun listData(query: String) : Flow<PagingData<Article>>  = getSearchData(query)






}