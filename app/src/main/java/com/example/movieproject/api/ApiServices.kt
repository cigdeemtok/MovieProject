package com.example.movieproject.api

import com.example.movieproject.response.MovieDetails
import com.example.movieproject.response.MovieList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {

    @GET("movie/popular")
    suspend fun getPopularMoviesList(@Query("page") page: Int) : Response<MovieList>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") movieId : Int) : Response<MovieDetails>

    @GET("search/movie")
    suspend fun searchMovies(@Query("query") query: String,
                                @Query("page") page : Int) : Response<MovieList>
}