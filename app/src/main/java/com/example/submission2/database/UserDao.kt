package com.example.submission2.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: UserEntity)

    @Delete
    fun delete(user: UserEntity)

    @Query("SELECT * FROM user")
    fun getAllFavorite(): LiveData<List<UserEntity>>

    @Query("SELECT * FROM user WHERE username = :username")
    fun getFavorite(username: String): LiveData<UserEntity>
}