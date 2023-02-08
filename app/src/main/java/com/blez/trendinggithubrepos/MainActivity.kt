package com.blez.trendinggithubrepos

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.blez.trendinggithubrepos.databinding.ActivityMainBinding
import com.blez.trendinggithubrepos.ui.news.MainViewModel
import com.blez.trendinggithubrepos.ui.news.TrendingNewsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    @Inject
    lateinit var trendingNewsAdapter: TrendingNewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        orientationChange()


        binding.switchOrientation.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                binding.switchOrientation.text = "Horizontal"
                orientationChange()

            } else
                binding.switchOrientation.text = "Vertical"
            orientationChange()

        }


        lifecycleScope.launch {
            mainViewModel.list.collect {
                binding.progressBar.isVisible = false

                trendingNewsAdapter.submitData(lifecycle, it)
                binding.recyclerView.adapter = trendingNewsAdapter
            }

        }


        trendingNewsAdapter.onItemClick = {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it.url))
            startActivity(intent)
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        val searchItem = menu?.findItem(R.id.searchBar)
        searchItem?.expandActionView()
        val search = searchItem?.actionView as SearchView


        val searchQuery = object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query.toString() != "")
                    searchQuery(query.toString())
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        }

        search.setOnQueryTextListener(searchQuery)
        search.setOnCloseListener {
            lifecycleScope.launch {
                mainViewModel.list.collect {
                    binding.progressBar.isVisible = false

                    trendingNewsAdapter.submitData(lifecycle, it)
                    binding.recyclerView.adapter = null
                    binding.recyclerView.adapter = trendingNewsAdapter
                }

            }
            return@setOnCloseListener false
        }
        return super.onCreateOptionsMenu(menu)
    }

    private fun searchQuery(query: String) {

        CoroutineScope(Dispatchers.Main).launch {
            mainViewModel.listData(query).collect {

                trendingNewsAdapter.submitData(lifecycle, it)
                trendingNewsAdapter.notifyDataSetChanged()

                binding.recyclerView.adapter = trendingNewsAdapter

                binding.recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
            }
        }
    }

    private fun orientationChange() {
        when (binding.switchOrientation.isChecked) {
            true -> {

                binding.recyclerView.layoutManager =
                    LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            }
            false -> {

                binding.recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
            }
        }
    }
}