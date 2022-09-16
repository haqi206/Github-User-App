package com.example.submission2.database

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.submission2.DetailActivity
import com.example.submission2.database.UserEntity
import com.example.submission2.database.UserDao
import com.example.submission2.database.UserDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class UserRepository(application: Application){
    private val mUserDao: UserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = UserDatabase.getDatabase(application)
        mUserDao = db.userDao()
    }

    fun getAllFavorite(): LiveData<List<UserEntity>> = mUserDao.getAllFavorite()

    fun insert(user: UserEntity){
        executorService.execute { mUserDao.insert(user) }
    }

    fun delete(user: UserEntity){
        executorService.execute { mUserDao.delete(user) }
    }
    fun getFavorite(): LiveData<UserEntity> = mUserDao.getFavorite(DetailActivity.forDetail)
}