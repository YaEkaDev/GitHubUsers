package com.example.githubusers.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable

@Dao
interface UserInfoDao {

    @Query("SELECT * FROM favourite_users ORDER BY id")
    fun getAllFavouriteUsers(): LiveData<List<UserInfoDbModel>>

    @Query("SELECT * FROM favourite_users WHERE id == :id LIMIT 1")
    fun getFavouriteUser(id: Int): LiveData<UserInfoDbModel>

    @Query("Delete FROM favourite_users WHERE id = :id")
    fun remove(id: Int):Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: UserInfoDbModel):Completable

}