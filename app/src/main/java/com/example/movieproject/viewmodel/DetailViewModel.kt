package com.example.movieproject.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieproject.db.FavoritesData
import com.example.movieproject.repository.ApiRepository
import com.example.movieproject.repository.DatabaseRepository
import com.example.movieproject.response.MovieDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val apiRepository: ApiRepository,
    private val dbRepository: DatabaseRepository
) : ViewModel()
{
    val movieDetails = MutableLiveData<MovieDetails?>()


    var favMovie = MutableLiveData<List<FavoritesData>>()

    val isLoading = MutableLiveData<Boolean>()

    val isFavMovie = MutableLiveData<Boolean>()

    fun loadMovieDetails(id: Int) = viewModelScope.launch {
       isLoading.postValue(true)
       val response = apiRepository.getMovieDetails(id)
       if (response.isSuccessful) {
           movieDetails.postValue(response.body())
       }
      isLoading.postValue(false)


    }
    fun refreshDatabase(id: Int){
        checkFavMovie(id)
    }

    fun favOrUnfav(isFav : Boolean){//toggle

        if(isFav){
            val movie = movieDetails.value?.let {
                FavoritesData(it.id,it.originalTitle,it.releaseDate,it.voteAverage,it.posterPath)
            }
            addToDb(movie!!)
            isFavMovie.postValue(true)
        }else{

            movieDetails.value?.let {
                deleteFromDb(it.id!!)
            }
            isFavMovie.postValue(false)
        }
    }

    fun loadAllFav(){
        getAllFav()

    }
    //get all favorites
    private fun getAllFav(){
        isLoading.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            val movie = dbRepository.getAllFav()
            favMovie.postValue( movie)
            isLoading.postValue(false)


        }
    }
    //check database if there is a movie with that id
    private fun checkFavMovie(id:Int){
        viewModelScope.launch(Dispatchers.IO) {
            val favCount = dbRepository.getFavMovie(id)
            isFavMovie.postValue(favCount>0)
        }
    }
    //add to fav
    private fun addToDb(movie : FavoritesData){
        viewModelScope.launch(Dispatchers.IO) {
            dbRepository.addToFav(movie)

        }
    }
    //delete from fav
    private fun deleteFromDb(id : Int){
        viewModelScope.launch(Dispatchers.IO) {
            dbRepository.deleteFav(id)

        }
    }





}