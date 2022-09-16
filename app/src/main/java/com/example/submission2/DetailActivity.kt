package com.example.submission2

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.submission2.adapter.SectionsPagerAdapter
import com.example.submission2.api.Detail
import com.example.submission2.database.UserEntity
import com.example.submission2.databinding.ActivityDetailBinding
import com.example.submission2.utils.FavViewModelFactory
import com.example.submission2.viewmodel.DetailMainModel
import com.example.submission2.viewmodel.FavoriteViewModel
import com.google.android.material.tabs.TabLayoutMediator


class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var favViewModel: FavoriteViewModel
    private lateinit var userEntity: UserEntity

    private val viewModel: DetailMainModel by viewModels()
    private var db = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Detail User"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel.detail.observe(this, { user ->
            setDetail(user)
        })

        viewModel.isLoading.observe(this, {
            showLoading(it)
        })

        val moveUser = intent.getStringExtra(EXTRA_USERNAME)

        moveUser?.let {
            val sectionsPagerAdapter = SectionsPagerAdapter(this, moveUser)
            binding.viewPager.adapter = sectionsPagerAdapter
            TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()
            supportActionBar?.elevation = 0f

            viewModel.getDetail(moveUser)
            viewModel.getFollowers(moveUser)
            viewModel.getFollowing(moveUser)
            forDetail = moveUser
            Log.d(TAG, "fordetail: ${forDetail}")
        }


        favViewModel = obtainViewModel(this@DetailActivity)
        favViewModel.getFavorite().observe(this) { user ->
            if (user != null) {
                binding.btnFavorite.setImageResource(R.drawable.ic_baseline_star_24)
                db = true
            }
        }
        binding.btnFavorite.setOnClickListener {
            if (db) {
                userEntity.let { user -> favViewModel.delete(user) }
                binding.btnFavorite.setImageResource(R.drawable.ic_baseline_star_border_24)
                Toast.makeText(
                    applicationContext,
                    "Favorite User deleted",
                    Toast.LENGTH_SHORT
                ).show()
                db = false
            } else {
                userEntity.let { user -> favViewModel.insert(user) }
                Toast.makeText(applicationContext, " Added to Favorite User", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun setDetail(items: Detail) {
        binding.tvItemUsername.text = "@" + items.login
        binding.tvName.text = items.name
        binding.followersValue.text = items.followers.toString()
        binding.followingValue.text = items.following.toString()
        binding.repoValue.text = items.publicRepos.toString()
        binding.tvCompany.text = items.company
        binding.tvLocation.text = items.location
        Glide.with(this@DetailActivity)
            .load(items.avatarUrl)
            .circleCrop()
            .into(binding.imgItemPhoto)
        userEntity = UserEntity(
            items.login,
            items.avatarUrl,
            items.name,
            items.company,
            items.location,
            items.publicRepos.toString(),
            items.followers.toString(),
            items.following.toString(),
        )
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = FavViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoriteViewModel::class.java]
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private const val TAG = "DetailActivity"
        const val EXTRA_USERNAME = "data_username"
        var forDetail = ""

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )

    }
}