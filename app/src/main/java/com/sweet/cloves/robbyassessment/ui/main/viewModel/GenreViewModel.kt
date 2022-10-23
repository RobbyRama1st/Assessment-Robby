package com.sweet.cloves.robbyassessment.ui.main.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sweet.cloves.robbyassessment.network.response.base.Result
import com.sweet.cloves.robbyassessment.network.response.GenreResponse
import com.sweet.cloves.robbyassessment.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenreViewModel @Inject constructor(
    private val repository: AppRepository,
) : ViewModel() {

    fun fetchGenre(apiKey: String, language: String) : LiveData<Result<GenreResponse>> {
        val genres = MutableLiveData<Result<GenreResponse>>()
        viewModelScope.launch {
            repository.fetchGenre(apiKey, language).collect {
                genres.value = it
            }
        }
        return genres
    }
}