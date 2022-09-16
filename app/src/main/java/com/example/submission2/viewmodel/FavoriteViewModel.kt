package com.example.submission2.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.submission2.database.UserEntity
import com.example.submission2.database.UserRepository

class FavoriteViewModel(application: Application) : ViewModel() {

    private val mUserRepository: UserRepository = UserRepository(application)

    fun getFavorite(): LiveData<UserEntity> = mUserRepository.getFavorite()

    fun getAllFavorite(): LiveData<List<UserEntity>> = mUserRepository.getAllFavorite()

    fun insert(user: UserEntity){
        mUserRepository.insert(user)
    }

    fun delete(user: UserEntity){
        mUserRepository.delete(user)
    }

}