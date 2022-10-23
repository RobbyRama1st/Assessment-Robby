package com.sweet.cloves.robbyassessment.model

import com.google.gson.annotations.SerializedName

data class MovieDetails(
    @SerializedName("adult")
    val adult: Boolean?,

    @SerializedName("backdrop_path")
    val backdropPath: String?,

//    @SerializedName("belong_to_collection")
//    val belongsToCollection: BelongsToCollection?,

    @SerializedName("budget")
    val budget: Long?,

    @SerializedName("genres")
    val genres: List<Genre>?,

    @SerializedName("homepage")
    val homepage: String?,

    @SerializedName("id")
    val id: Long?,

    @SerializedName("imdb_id")
    val imdbID: String?,

    @SerializedName("original_language")
    val originalLanguage: String?,

    @SerializedName("original_title")
    val originalTitle: String?,

    @SerializedName("overview")
    val overview: String?,

    @SerializedName("popularity")
    val popularity: Double?,

    @SerializedName("poster_path")
    val posterPath: String?,

//    @SerializedName("production_companies")
//    val productionCompanies: List<ProductionCompany>?,

//    @SerializedName("production_countries")
//    val productionCountries: List<ProductionCountry>?,

    @SerializedName("release_date")
    val releaseDate: String?,

    @SerializedName("revenue")
    val revenue: Long?,

    @SerializedName("runtime")
    val runtime: Long?,

//    @SerializedName("spoken_languages")
//    val spokenLanguages: List<SpokenLanguage>?,

    @SerializedName("status")
    val status: String?,

    @SerializedName("tagline")
    val tagline: String?,

    @SerializedName("title")
    val title: String?,

    @SerializedName("video")
    val video: Boolean?,

    @SerializedName("vote_average")
    val voteAverage: Double?,

    @SerializedName("vote_count")
    val voteCount: Long?
)