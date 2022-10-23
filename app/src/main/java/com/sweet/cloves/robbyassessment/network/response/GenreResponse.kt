package com.sweet.cloves.robbyassessment.network.response

import com.google.gson.annotations.SerializedName
import com.sweet.cloves.robbyassessment.model.Genre

data class GenreResponse(

    @SerializedName("genres")
    var genres: ArrayList<Genre>
)
