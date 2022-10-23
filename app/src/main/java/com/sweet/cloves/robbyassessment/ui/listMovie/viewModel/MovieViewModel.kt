package com.sweet.cloves.robbyassessment.ui.listMovie.viewModel

import androidx.lifecycle.*
import com.sweet.cloves.robbyassessment.network.response.base.Result
import com.sweet.cloves.robbyassessment.network.response.MovieResponse
import com.sweet.cloves.robbyassessment.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val repository: AppRepository,
) : ViewModel() {
    fun fetchMovie(apiKey: String, genreID: String, page: Int) : LiveData<Result<MovieResponse>> {
        val movies = MutableLiveData<Result<MovieResponse>>()
        viewModelScope.launch {
            repository.fetchMovie(apiKey, genreID, page.toString()).collect {
                movies.value = it
            }
        }
        return movies
    }
}