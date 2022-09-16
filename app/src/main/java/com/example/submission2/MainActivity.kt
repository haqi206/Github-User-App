package com.example.submission2

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission2.adapter.ListUserAdapter
import com.example.submission2.api.ItemsItem
import com.example.submission2.databinding.ActivityMainBinding
import com.example.submission2.viewmodel.MainViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Github Users Search"

        val layoutManager = LinearLayoutManager(this)
        binding.rvGhuser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvGhuser.addItemDecoration(itemDecoration)

        viewModel.user.observe(this, { user ->
            setUserResponse(user)
        })

        viewModel.isLoading.observe(this, {
            showLoading(it)
        })

    }

    private fun setUserResponse(items: List<ItemsItem>) {
        val listUser = ArrayList<ItemsItem>()
        for (data in items) {
            val result = ItemsItem(data.login, data.avatarUrl, data.url)
            listUser.add(result)
        }
        val adapter = ListUserAdapter(listUser)
        binding.rvGhuser.adapter = adapter
        adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ItemsItem) {
                showSelectedUser(data)
            }
        })
    }

    private fun showSelectedUser(user: ItemsItem) {
        val intentToDetail = Intent(this, DetailActivity::class.java)
        intentToDetail.putExtra(DetailActivity.EXTRA_USERNAME, user.login)
        startActivity(intentToDetail)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText == "") {
                    val listResult = ArrayList<ItemsItem>()
                    setUserResponse(listResult)
                } else {
                    viewModel.listUser(newText)
                }
                return true
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.switch_theme -> {
                val i = Intent(this, ThemeActivity::class.java)
                startActivity(i)
                return true
            }
            R.id.ic_favorite -> {
                val i = Intent(this, FavoriteActivity::class.java)
                startActivity(i)
                return true
            }
            else -> return true
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}