package com.example.movieproject.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movieproject.repository.ApiRepository
import com.example.movieproject.repository.DatabaseRepository
import com.example.movieproject.response.MovieList
import retrofit2.HttpException

class MovieListPagingSource(private val apiRepository: ApiRepository,
       private val dbRepository: DatabaseRepository
)
    : PagingSource<Int, MovieList.Result>() {


    override fun getRefreshKey(state: PagingState<Int, MovieList.Result>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieList.Result> {
        return try {
            val currentPage = params.key ?: 1
            val response = apiRepository.getPopularMoviesList(currentPage)
            val data = response.body()!!.results
            val responseData = mutableListOf<MovieList.Result>()


            responseData.addAll(data!!.toMutableList())

            LoadResult.Page(
                data = responseData,
                prevKey = if ( currentPage == 1) null else -1,
                nextKey = if(responseData.isEmpty()) null else currentPage.plus(1)
            )

        }catch (e: Exception){
            LoadResult.Error(e)

        }catch (httpError : HttpException){
            LoadResult.Error(httpError)
        }
    }

}