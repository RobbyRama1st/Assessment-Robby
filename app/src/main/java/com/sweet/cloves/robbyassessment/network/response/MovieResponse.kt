package com.sweet.cloves.robbyassessment.network.response

import com.google.gson.annotations.SerializedName
import com.sweet.cloves.robbyassessment.model.Movie

class MovieResponse(

    @SerializedName("page")
    val page: Int,

    @SerializedName("results")
    val results: ArrayList<Movie>,

    @SerializedName("total_pages")
    val totalPages: Int,

    @SerializedName("total_results")
    val totalResults: Int,
)