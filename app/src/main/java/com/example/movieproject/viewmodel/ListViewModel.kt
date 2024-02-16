package com.example.movieproject.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.qualifiers.ApplicationContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.movieproject.db.FavoritesData
import com.example.movieproject.paging.MovieListPagingSource
import com.example.movieproject.paging.SearchMoviePaging
import com.example.movieproject.repository.ApiRepository
import com.example.movieproject.repository.DatabaseRepository
import com.example.movieproject.response.MovieList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ListViewModel @Inject constructor(
    private val apiRepository: ApiRepository,
    private val dbRepository : DatabaseRepository
)
    : ViewModel()
{
    private val query = MutableStateFlow<String>("")


    //val isFavMovie = MutableLiveData<Boolean>()

    //val count = MutableLiveData<Int>()



    //get list data
    var movieList = Pager(PagingConfig(1)){
        MovieListPagingSource(apiRepository,dbRepository)
    }.flow.cachedIn(viewModelScope)



    //set query for search view
    fun setQuery(s : String){
        query.update {
            s
        }
    }

    //get searchview response
    val list = query.flatMapLatest {

        Pager(PagingConfig(1)) {
            SearchMoviePaging(it, apiRepository)
        }.flow.cachedIn(viewModelScope)

    }

    //fav operations for paging list
    fun checkIfFav(id: Int) : Boolean{
        return checkFavMovie(id)
    }
   /* fun checkFavStatus(movie : MovieList.Result){
        changeFavStatus(movie)
    }*/

    fun favOrUnfav(isFav : Boolean,movie : MovieList.Result){//toggle

        if(isFav){
            val favMovie = FavoritesData(movie.id,movie.originalTitle,movie.releaseDate,movie.voteAverage,movie.posterPath)
            addToDb(favMovie)
            //isFavMovie.postValue(true)

        }

        else{
            deleteFromDb(movie.id!!)
            //isFavMovie.postValue(false)
        }
    }

    //check database if there is a movie with that id
    private fun checkFavMovie(id:Int) : Boolean{
        return id == dbRepository.checkIfFav(id)
        //count.postValue(favCount)
        //isFavMovie.postValue(favCount>0)

    }
    /*private fun changeFavStatus(movie : MovieList.Result){
        viewModelScope.launch(Dispatchers.IO) {
            val favList = database.getFavDao().getAllFav()
            favList.map { favMovie->
                if(movie.id == favMovie.id){
                    movie.isFav = true
                }
            }
        }

    }*/
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