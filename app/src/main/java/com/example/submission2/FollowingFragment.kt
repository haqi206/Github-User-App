package com.example.submission2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission2.api.FollowingResponseItem
import com.example.submission2.adapter.FollowingAdapter
import com.example.submission2.databinding.FragmentFollowingBinding
import com.example.submission2.viewmodel.DetailMainModel


class FollowingFragment(userId: String) : Fragment() {
    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!
    val name = userId
    private val viewModel: DetailMainModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.listFollowing.observe(viewLifecycleOwner, { user ->
            setFollowing(user)
        })

        viewModel.isLoading.observe(viewLifecycleOwner, {
            showLoading(it)
        })

        viewModel.getFollowing(name)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun setFollowing(items: List<FollowingResponseItem>) {
        binding.rvFollows.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(activity)
        binding.rvFollows.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(activity, layoutManager.orientation)
        binding.rvFollows.addItemDecoration(itemDecoration)
        val adapter = FollowingAdapter(items)
        binding.rvFollows.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean){
        if (isLoading){
            binding.progressBar.visibility  = View.VISIBLE
        }else{
            binding.progressBar.visibility  = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val TAG = "FragmentFollowing"
        private const val USER_NAME = "user_name"
        @JvmStatic
        fun userName(username: String) =
            FollowingFragment(userId = username).apply {
                arguments = Bundle().apply {
                    putString(USER_NAME, username)
                }
            }
    }
}

