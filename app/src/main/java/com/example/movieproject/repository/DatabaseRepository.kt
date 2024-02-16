package com.example.movieproject.repository

import com.example.movieproject.db.FavoritesDao
import com.example.movieproject.db.FavoritesData
import javax.inject.Inject

class DatabaseRepository @Inject constructor(private val favoritesDao: FavoritesDao)
{
    fun getAllFav() = favoritesDao.getAllFav()
    fun addToFav(movie:FavoritesData) = favoritesDao.addToFav(movie)
    fun deleteFav(id: Int) = favoritesDao.deleteFav(id)
    fun getFavMovie(id :Int) = favoritesDao.getFavMovie(id)
    fun checkIfFav(id:Int) = favoritesDao.checkIfFav(id)

}