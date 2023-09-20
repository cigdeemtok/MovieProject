package com.example.movieproject.db

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

class MovieData(
    @PrimaryKey(false)
    val id: Int?,
    @ColumnInfo("original_title")
    val originalTitle: String?,
    @ColumnInfo("release_date")
    val releaseDate: String?,
    @ColumnInfo("vote_average")
    val voteAverage: Double?,
    @ColumnInfo("poster_path")
    val posterPath: String?,


)