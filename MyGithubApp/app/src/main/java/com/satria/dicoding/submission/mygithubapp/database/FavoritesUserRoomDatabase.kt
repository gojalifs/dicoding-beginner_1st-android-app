package com.satria.dicoding.submission.mygithubapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoriteUser::class], version = 1)
abstract class FavoritesUserRoomDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoritesUserDao

    companion object {
        @Volatile
        private var INSTANCE: FavoritesUserRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): FavoritesUserRoomDatabase {
            if (INSTANCE == null) {
                synchronized(FavoritesUserRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        FavoritesUserRoomDatabase::class.java, "favs_user_db"
                    ).build()
                }
            }
            return INSTANCE as FavoritesUserRoomDatabase
        }
    }
}