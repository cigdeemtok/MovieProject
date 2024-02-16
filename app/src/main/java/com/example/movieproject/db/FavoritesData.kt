package com.example.movieproject.db

import android.graphics.drawable.Drawable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movieproject.utils.Constants.TABLE_NAME

@Entity(
    tableName =  TABLE_NAME

)
data class FavoritesData(

    @ColumnInfo("id")
    var id: Int?,
    @ColumnInfo("original_title")
    var originalTitle: String?,
    @ColumnInfo("release_date")
    var releaseDate: String?,
    @ColumnInfo("vote_average")
    var voteAverage: Double?,
    @ColumnInfo("poster_path")
    var posterPath: String?,
)
{
    @PrimaryKey(autoGenerate = true)
    var dbId : Int = 0
}