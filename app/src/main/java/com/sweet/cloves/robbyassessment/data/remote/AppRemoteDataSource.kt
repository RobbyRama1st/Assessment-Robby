package com.sweet.cloves.robbyassessment.data.remote

import com.sweet.cloves.robbyassessment.model.MovieDetails
import com.sweet.cloves.robbyassessment.network.response.base.Result
import com.sweet.cloves.robbyassessment.network.AppService
import com.sweet.cloves.robbyassessment.network.response.GenreResponse
import com.sweet.cloves.robbyassessment.network.response.MovieResponse
import com.sweet.cloves.robbyassessment.network.response.ReviewResponse
import com.sweet.cloves.robbyassessment.network.response.TrailerResponse
import com.sweet.cloves.robbyassessment.util.ErrorUtil
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class AppRemoteDataSource @Inject constructor(private val retrofit: Retrofit) {

    suspend fun fetchGenre(apiKey: String?, language: String?) : Result<GenreResponse> {
        val appService = retrofit.create(AppService::class.java);
        return getResponse(
            request = { appService.getGenre(apiKey, language) },
            defaultErrorMessage = "Error fetching Genre List")
    }

    suspend fun fetchMovie(apiKey: String?, genreID: String?, page: String?) : Result<MovieResponse> {
        val appService = retrofit.create(AppService::class.java)
        return getResponse(
            request = { appService.getMovieByGenre(apiKey, genreID, page) },
            defaultErrorMessage = "Error fetching Movie List"
        )
    }

    suspend fun fetchMovieDetail(apiKey: String?, movieID: String?, language: String?) : Result<MovieDetails> {
        val appService = retrofit.create(AppService::class.java)
        return getResponse(
            request = { appService.getMovieDetailsById(apiKey, movieID, language) },
            defaultErrorMessage = "Error fetching Movie Details"
        )
    }

    suspend fun fetchMovieTrailer(movieID: String?, apiKey: String?) : Result<TrailerResponse> {
        val appService = retrofit.create(AppService::class.java)
        return getResponse(
            request = { appService.getMovieTrailerById(movieID, apiKey)},
            defaultErrorMessage = "Error fetching Movie Trailer"
        )
    }

    suspend fun fetchMovieReview(movieID: String?, apiKey: String?, language: String?, page: String?) : Result<ReviewResponse> {
        val appService = retrofit.create(AppService::class.java)
        return getResponse(
            request = { appService.getMovieReviewById( movieID, apiKey, language, page) },
            defaultErrorMessage = "Error fetching Movie Review"
        )
    }

    private suspend fun <T> getResponse(request: suspend () -> Response<T>, defaultErrorMessage: String): Result<T> {
        return try {
            println("I'm working in thread ${Thread.currentThread().name}")
            val result = request.invoke()
            if (result.isSuccessful) {
                return Result.success(result.body())
            } else {
                val errorResponse = ErrorUtil.parseError(result, retrofit)
                Result.error(errorResponse?.message ?: defaultErrorMessage, errorResponse)
            }
        } catch (e: Throwable) {
            println("Error : ${e.message.toString()}")
            Result.error("Error : " + e.message.toString(), null)
        }
    }
}