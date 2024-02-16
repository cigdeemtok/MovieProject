package com.example.movieproject.repository

import com.example.movieproject.api.ApiServices
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject


class ApiRepository @Inject constructor(
    private val apiServices: ApiServices)
{

    suspend fun getPopularMoviesList(page : Int) = apiServices.getPopularMoviesList(page)

    suspend fun getMovieDetails(id : Int) = apiServices.getMovieDetails(id)

    suspend fun searchMovies(query : String, page: Int) = apiServices.searchMovies(query,page)
}