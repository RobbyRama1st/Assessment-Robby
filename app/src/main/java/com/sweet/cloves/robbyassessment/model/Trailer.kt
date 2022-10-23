package com.sweet.cloves.robbyassessment.model

import com.google.gson.annotations.SerializedName

data class Trailer(
    @SerializedName("iso639_1")
    val iso639_1: String,
    @SerializedName("iso3166_1")
    val iso3166_1: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("key")
    val key: String,
    @SerializedName("site")
    val site: String,
    @SerializedName("size")
    val size: Long,
    @SerializedName("type")
    val type: String,
    @SerializedName("official")
    val official: Boolean,
    @SerializedName("published_at")
    val publishedAt: String,
    @SerializedName("id")
    val id: String
)