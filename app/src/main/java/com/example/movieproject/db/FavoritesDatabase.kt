package com.example.movieproject.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [FavoritesData::class],
    version = 1

)
abstract class FavoritesDatabase  : RoomDatabase(){
    abstract fun getFavDao() : FavoritesDao

}