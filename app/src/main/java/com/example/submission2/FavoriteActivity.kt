package com.example.submission2

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission2.adapter.FavoriteAdapter
import com.example.submission2.database.UserEntity
import com.example.submission2.databinding.ActivityFavoriteBinding
import com.example.submission2.utils.FavViewModelFactory
import com.example.submission2.viewmodel.FavoriteViewModel


class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Favorite"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val userFavoriteViewModel = obtainViewModel(this)
        userFavoriteViewModel.getAllFavorite().observe(this, { user ->
            if (user != null) {
                adapter.listFavorite(user)
            }
        })

        adapter = FavoriteAdapter()
        val layoutManager = LinearLayoutManager(this)
        binding.rvFav.layoutManager = layoutManager
        binding.rvFav.adapter = adapter

        adapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserEntity) {
                moveToDetail(data)
            }
        })
    }


    private fun moveToDetail(data: UserEntity) {
        val intentToDetail = Intent(this, DetailActivity::class.java)
        intentToDetail.putExtra(DetailActivity.EXTRA_USERNAME, data.userName)
        startActivity(intentToDetail)
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = FavViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoriteViewModel::class.java]
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }

        }
        return super.onOptionsItemSelected(item)
    }
}