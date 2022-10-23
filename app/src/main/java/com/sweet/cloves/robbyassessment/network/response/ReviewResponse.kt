package com.sweet.cloves.robbyassessment.network.response

import com.google.gson.annotations.SerializedName
import com.sweet.cloves.robbyassessment.model.review.Review

class ReviewResponse(

    @SerializedName("id")
    val id: Int,

    @SerializedName("page")
    val page: Int,

    @SerializedName("results")
    val results: ArrayList<Review>,

    @SerializedName("total_pages")
    val totalPages: Int,

    @SerializedName("total_results")
    val totalResults: Int,
)