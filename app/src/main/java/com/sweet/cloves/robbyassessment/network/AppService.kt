package com.sweet.cloves.robbyassessment.network

import com.sweet.cloves.robbyassessment.Constant
import com.sweet.cloves.robbyassessment.model.*
import com.sweet.cloves.robbyassessment.network.response.GenreResponse
import com.sweet.cloves.robbyassessment.network.response.MovieResponse
import com.sweet.cloves.robbyassessment.network.response.ReviewResponse
import com.sweet.cloves.robbyassessment.network.response.TrailerResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AppService {

    @GET("3/genre/movie/list")
    suspend fun getGenre(
        @Query("api_key") apiKey: String?,
        @Query("language") language: String?,
    ) : Response<GenreResponse>

    @GET("3/discover/movie")
    suspend fun getMovieByGenre(
        @Query("api_key") apiKey: String?,
        @Query("with_genres") genreID: String?,
        @Query("page") page: String?,
        // @Query("sort_by") shortBy: String = Constant.PRIMARY_RELEASE_DATE_DESC
    ) : Response<MovieResponse>

    @GET("/3/movie/{movieID}")
    suspend fun getMovieDetailsById(
        @Path("movieID") id: String?,
        @Query("api_key") apiKey: String?,
        @Query("language") language: String?
    ) : Response<MovieDetails>

    @GET("3/movie/{movieID}/videos")
    suspend fun getMovieTrailerById(
        @Path("movieID") movieID: String?,
        @Query("api_key") apiKey: String?,
    ) : Response<TrailerResponse>

    @GET("3/movie/{movieID}/reviews")
    suspend fun getMovieReviewById(
        @Path("movieID") movieID: String?,
        @Query("api_key") apiKey: String?,
        @Query("language") language: String?,
        @Query("page") page: String?
    ) : Response<ReviewResponse>
}