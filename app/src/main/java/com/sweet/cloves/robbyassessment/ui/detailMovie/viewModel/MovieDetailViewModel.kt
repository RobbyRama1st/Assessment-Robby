package com.sweet.cloves.robbyassessment.ui.detailMovie.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sweet.cloves.robbyassessment.model.MovieDetails
import com.sweet.cloves.robbyassessment.model.review.Review
import com.sweet.cloves.robbyassessment.network.response.ReviewResponse
import com.sweet.cloves.robbyassessment.network.response.TrailerResponse
import com.sweet.cloves.robbyassessment.network.response.base.Result
import com.sweet.cloves.robbyassessment.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val repository: AppRepository,
) : ViewModel () {

    fun fetchMovieDetail(movieID: String, apiKey: String,  language: String, page: String) : LiveData<Result<MovieDetails>>{
        val movieDetails = MutableLiveData<Result<MovieDetails>>()

        viewModelScope.launch {
            repository.fetchMovieDetail(movieID, apiKey, language).collect {
                movieDetails.value = it
            }
        }
        return movieDetails
    }

    fun fetchMovieTrailer(movieID: String, apiKey: String) : LiveData<Result<TrailerResponse>> {
        val trailer = MutableLiveData<Result<TrailerResponse>>()

        viewModelScope.launch {
            repository.fetchMovieTrailer(movieID, apiKey).collect {
                trailer.value = it
            }
        }
        return trailer
    }

    fun fetchMovieReview(movieID: String, apiKey: String, language: String, page: String) : LiveData<Result<ReviewResponse>> {
        val reviews = MutableLiveData<Result<ReviewResponse>>()

        viewModelScope.launch {
            repository.fetchMovieReview(movieID, apiKey, language, page).collect {
                reviews.value = it
            }
        }
        return reviews
    }
}