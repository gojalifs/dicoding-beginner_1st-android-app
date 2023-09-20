package com.satria.dicoding.submission.mygithubapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoritesUserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: FavoriteUser)

    @Delete
    fun delete(user: FavoriteUser)

    @Query("SELECT * FROM FavoriteUser ORDER BY username ASC")
    fun getAllFavoritesUser() : LiveData<List<FavoriteUser>>

    @Query("SELECT * FROM FavoriteUser WHERE USERNAME = :username")
    fun getFavoriteUserByUsername(username:String):LiveData<FavoriteUser>
}