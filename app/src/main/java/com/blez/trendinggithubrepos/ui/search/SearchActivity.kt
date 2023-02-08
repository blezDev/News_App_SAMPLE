package com.blez.trendinggithubrepos.ui.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.blez.trendinggithubrepos.R
import com.blez.trendinggithubrepos.databinding.ActivitySearchBinding
import com.blez.trendinggithubrepos.ui.news.TrendingNewsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySearchBinding

    @Inject
     lateinit var searchAdapter : TrendingNewsAdapter

    private lateinit var searchViewModel: SearchViewModel
    private var searchJob : Job?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_search)
        searchViewModel = ViewModelProvider(this)[SearchViewModel::class.java]
        orientationChange()


    }

    private fun orientationChange() {
        when(binding.orientationChangeSwitch.isChecked)
        {
            true->{
                binding.SearchRecyclerview.layoutManager = LinearLayoutManager(this@SearchActivity,
                    LinearLayoutManager.HORIZONTAL,false)
            }
            false->{
                binding.SearchRecyclerview.layoutManager = LinearLayoutManager(this@SearchActivity)
            }
        }
    }

    private fun searchQuery(query: String){

            searchJob = CoroutineScope(Dispatchers.Main).launch {
          searchViewModel.list(query).collect{
              searchAdapter.submitData(it)
              binding.SearchRecyclerview.adapter = searchAdapter
          }
      }
    }
}