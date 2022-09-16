package com.example.submission2.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission2.api.ApiConfig
import com.example.submission2.api.Detail
import com.example.submission2.api.FollowersResponseItem
import com.example.submission2.api.FollowingResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailMainModel : ViewModel() {
    private val _detail = MutableLiveData<Detail>()
    val detail: LiveData<Detail> = _detail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listFoll = MutableLiveData<List<FollowersResponseItem>>()
    val listFoll: LiveData<List<FollowersResponseItem>> = _listFoll

    private val _listFollowing = MutableLiveData<List<FollowingResponseItem>>()
    val listFollowing: LiveData<List<FollowingResponseItem>> = _listFollowing

    companion object {
        private const val TAG = "DetailViewModel"
    }

    fun getDetail(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<Detail> {
            override fun onResponse(call: Call<Detail>, response: Response<Detail>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _detail.value = responseBody!!
                        Log.d(TAG, "Ditel: ${username}")
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }
            }

            override fun onFailure(call: Call<Detail>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun getFollowers(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(username)
        client.enqueue(object : Callback<List<FollowersResponseItem>> {
            override fun onResponse(
                call: Call<List<FollowersResponseItem>>,
                response: Response<List<FollowersResponseItem>>
            ) {
                _isLoading.value = false
                if ((response.isSuccessful) && response.body() != null) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listFoll.value = responseBody!!
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }
            }

            override fun onFailure(call: Call<List<FollowersResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun getFollowing(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object : Callback<List<FollowingResponseItem>> {
            override fun onResponse(
                call: Call<List<FollowingResponseItem>>,
                response: Response<List<FollowingResponseItem>>
            ) {
                _isLoading.value = false
                if ((response.isSuccessful) && response.body() != null) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listFollowing.value = responseBody!!
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                }
            }

            override fun onFailure(call: Call<List<FollowingResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}