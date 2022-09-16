package com.example.submission2.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity (tableName = "user")
data class UserEntity(
    @PrimaryKey
    @ColumnInfo(name = "username")
    var userName: String,

    @ColumnInfo(name = "photo")
    var photo: String? = null,

    @ColumnInfo(name = "name")
    var name: String? = null,

    @ColumnInfo(name = "company")
    var company: String? = null,

    @ColumnInfo(name = "location")
    var location: String? = null,

    @ColumnInfo(name = "repository")
    var repository: String? = null,

    @ColumnInfo(name = "followers")
    var followers: String? = null,

    @ColumnInfo(name = "following")
    var following: String? = null
) : Parcelable

