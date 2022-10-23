package com.sweet.cloves.robbyassessment.repository

import com.sweet.cloves.robbyassessment.data.remote.AppRemoteDataSource
import com.sweet.cloves.robbyassessment.model.MovieDetails
import com.sweet.cloves.robbyassessment.network.response.base.Result
import com.sweet.cloves.robbyassessment.network.response.GenreResponse
import com.sweet.cloves.robbyassessment.network.response.MovieResponse
import com.sweet.cloves.robbyassessment.network.response.ReviewResponse
import com.sweet.cloves.robbyassessment.network.response.TrailerResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val appRemoteDataSource: AppRemoteDataSource
) {
    suspend fun fetchGenre(apiKey : String?, language: String?): Flow<Result<GenreResponse>> {
        return flow {
            emit(Result.loading())
            emit(appRemoteDataSource.fetchGenre(apiKey, language))
        }.flowOn(Dispatchers.IO)
    }

    suspend fun fetchMovie(apiKey: String?, genreID: String?, page: String?) : Flow<Result<MovieResponse>> {
        return flow {
            emit(Result.loading())
            emit(appRemoteDataSource.fetchMovie(apiKey, genreID, page))
        }.flowOn(Dispatchers.IO)
    }

    suspend fun fetchMovieDetail(movieID: String?, apiKey: String?,  language: String?) : Flow<Result<MovieDetails>> {
        return flow {
            emit(Result.loading())
            emit(appRemoteDataSource.fetchMovieDetail(movieID, apiKey, language))
        }.flowOn(Dispatchers.IO)
    }

    suspend fun fetchMovieTrailer(movieID: String?, apiKey : String?) : Flow<Result<TrailerResponse>> {
        return flow {
            emit(Result.loading())
            emit(appRemoteDataSource.fetchMovieTrailer(movieID, apiKey))
        }
    }

    suspend fun fetchMovieReview(movieID: String?, apiKey: String?, language: String?, page: String?) : Flow<Result<ReviewResponse>> {
        return flow {
            emit(Result.loading())
            emit(appRemoteDataSource.fetchMovieReview(movieID, apiKey,language, page))
        }.flowOn(Dispatchers.IO)
    }
}