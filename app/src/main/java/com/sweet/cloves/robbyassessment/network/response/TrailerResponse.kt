package com.sweet.cloves.robbyassessment.network.response

import com.google.gson.annotations.SerializedName
import com.sweet.cloves.robbyassessment.model.Trailer

data class TrailerResponse(

    @SerializedName("id")
    val id: Long,

    @SerializedName("results")
    val results: ArrayList<Trailer>,
)