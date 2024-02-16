package com.example.movieproject.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movieproject.response.MovieList
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao {

    @Query("SELECT * FROM favoriteMovies")
    fun getAllFav() : List<FavoritesData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addToFav(movie : FavoritesData)

    @Query("DELETE FROM favoriteMovies WHERE favoriteMovies.id = :id ")
    fun deleteFav(id : Int)

    @Query("SELECT COUNT(*) FROM favoriteMovies WHERE favoriteMovies.id = :id ")
    fun getFavMovie(id : Int) : Int//get fav mov count

    @Query("SELECT favoriteMovies.id FROM favoriteMovies WHERE favoriteMovies.id = :id ")
    fun checkIfFav(id : Int) : Int//if fav mov
}