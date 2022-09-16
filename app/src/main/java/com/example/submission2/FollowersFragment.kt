package com.example.submission2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission2.api.FollowersResponseItem
import com.example.submission2.adapter.FollowersAdapter
import com.example.submission2.databinding.FragmentFollowersBinding
import com.example.submission2.viewmodel.DetailMainModel


class FollowersFragment(userId: String) : Fragment() {

    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding!!
    val name = userId
    private val viewModel: DetailMainModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.listFoll.observe(viewLifecycleOwner, { user ->
            setFollowers(user)
        })

        viewModel.isLoading.observe(viewLifecycleOwner, {
            showLoading(it)
        })

        viewModel.getFollowers(name)
        Log.d(TAG, "getFollowers: ${name}")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun setFollowers(items: List<FollowersResponseItem>) {
        binding.rvFollows.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(activity)
        binding.rvFollows.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(activity, layoutManager.orientation)
        binding.rvFollows.addItemDecoration(itemDecoration)
        val adapter = FollowersAdapter(items)
        binding.rvFollows.adapter = adapter

        val intentToDetail = Intent(requireActivity(), DetailActivity::class.java)
        intentToDetail.putExtra(DetailActivity.EXTRA_USERNAME, name)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun showLoading(isLoading: Boolean){
        if (isLoading){
            binding.progressBar.visibility  = View.VISIBLE
        }else{
            binding.progressBar.visibility  = View.GONE
        }
    }

    companion object {
        private const val TAG = "FragmentFollowers"
        const val EXTRA_FOLLOWERS = "extra_followers"
        private const val USER_NAME = "user_name"
        @JvmStatic
        fun userName(username: String) =
            FollowersFragment(userId = username).apply {
                arguments = Bundle().apply {
                    putString(USER_NAME, username)
                }
            }
    }
}



