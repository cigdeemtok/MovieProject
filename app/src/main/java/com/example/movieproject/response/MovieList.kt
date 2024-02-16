package com.example.movieproject.response


import com.google.gson.annotations.SerializedName

data class MovieList(
    @SerializedName("page")
    val page: Int?, // 1
    @SerializedName("results")
    val results: List<Result>?,
    @SerializedName("total_pages")
    val totalPages: Int?, // 39389
    @SerializedName("total_results")
    val totalResults: Int? // 787774
) {
    data class Result(
        @SerializedName("adult")
        val adult: Boolean?, // false
        @SerializedName("backdrop_path")
        val backdropPath: String?, // /nHf61UzkfFno5X1ofIhugCPus2R.jpg
        @SerializedName("genre_ids")
        val genreIds: List<Int?>?,
        @SerializedName("id")
        val id: Int?, // 346698
        @SerializedName("original_language")
        val originalLanguage: String?, // en
        @SerializedName("original_title")
        val originalTitle: String?, // Barbie
        @SerializedName("overview")
        val overview: String?, // Barbie and Ken are having the time of their lives in the colorful and seemingly perfect world of Barbie Land. However, when they get a chance to go to the real world, they soon discover the joys and perils of living among humans.
        @SerializedName("popularity")
        val popularity: Double?, // 3447.567
        @SerializedName("poster_path")
        val posterPath: String?, // /iuFNMS8U5cb6xfzi51Dbkovj7vM.jpg
        @SerializedName("release_date")
        val releaseDate: String?, // 2023-07-19
        @SerializedName("title")
        val title: String?, // Barbie
        @SerializedName("video")
        val video: Boolean?, // false
        @SerializedName("vote_average")
        val voteAverage: Double?, // 7.5
        @SerializedName("vote_count")
        val voteCount: Int?, // 2102
        @SerializedName("isFav")
        var isFav: Boolean
    )
}