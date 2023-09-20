package com.example.movieproject.response


import com.google.gson.annotations.SerializedName

data class MovieDetails(
    @SerializedName("adult")
    val adult: Boolean?, // false
    @SerializedName("backdrop_path")
    val backdropPath: String?, // /hZkgoQYus5vegHoetLkCJzb17zJ.jpg
    @SerializedName("belongs_to_collection")
    val belongsToCollection: Any?, // null
    @SerializedName("budget")
    val budget: Int?, // 63000000
    @SerializedName("genres")
    val genres: List<Genre?>?,
    @SerializedName("homepage")
    val homepage: String?, // http://www.foxmovies.com/movies/fight-club
    @SerializedName("id")
    val id: Int?, // 550
    @SerializedName("imdb_id")
    val imdbId: String?, // tt0137523
    @SerializedName("original_language")
    val originalLanguage: String?, // en
    @SerializedName("original_title")
    val originalTitle: String?, // Fight Club
    @SerializedName("overview")
    val overview: String?, // A ticking-time-bomb insomniac and a slippery soap salesman channel primal male aggression into a shocking new form of therapy. Their concept catches on, with underground "fight clubs" forming in every town, until an eccentric gets in the way and ignites an out-of-control spiral toward oblivion.
    @SerializedName("popularity")
    val popularity: Double?, // 61.416
    @SerializedName("poster_path")
    val posterPath: String?, // /pB8BM7pdSp6B6Ih7QZ4DrQ3PmJK.jpg
    @SerializedName("production_companies")
    val productionCompanies: List<ProductionCompany?>?,
    @SerializedName("production_countries")
    val productionCountries: List<ProductionCountry?>?,
    @SerializedName("release_date")
    val releaseDate: String?, // 1999-10-15
    @SerializedName("revenue")
    val revenue: Int?, // 100853753
    @SerializedName("runtime")
    val runtime: Int?, // 139
    @SerializedName("spoken_languages")
    val spokenLanguages: List<SpokenLanguage?>?,
    @SerializedName("status")
    val status: String?, // Released
    @SerializedName("tagline")
    val tagline: String?, // Mischief. Mayhem. Soap.
    @SerializedName("title")
    val title: String?, // Fight Club
    @SerializedName("video")
    val video: Boolean?, // false
    @SerializedName("vote_average")
    val voteAverage: Double?, // 8.433
    @SerializedName("vote_count")
    val voteCount: Int? // 26280
) {
    data class Genre(
        @SerializedName("id")
        val id: Int?, // 18
        @SerializedName("name")
        val name: String? // Drama
    )

    data class ProductionCompany(
        @SerializedName("id")
        val id: Int?, // 508
        @SerializedName("logo_path")
        val logoPath: String?, // /7cxRWzi4LsVm4Utfpr1hfARNurT.png
        @SerializedName("name")
        val name: String?, // Regency Enterprises
        @SerializedName("origin_country")
        val originCountry: String? // US
    )

    data class ProductionCountry(
        @SerializedName("iso_3166_1")
        val iso31661: String?, // US
        @SerializedName("name")
        val name: String? // United States of America
    )

    data class SpokenLanguage(
        @SerializedName("english_name")
        val englishName: String?, // English
        @SerializedName("iso_639_1")
        val iso6391: String?, // en
        @SerializedName("name")
        val name: String? // English
    )
}