package com.vaibhavwani.stackoverflowapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.vaibhavwani.stackoverflowapp.model.Answer
import com.vaibhavwani.stackoverflowapp.model.ApiResponseWrapper
import com.vaibhavwani.stackoverflowapp.model.Question
import com.vaibhavwani.stackoverflowapp.model.network.StackOverflowRetrofitService
import com.vaibhavwani.stackoverflowapp.viewmodel.UiState.Loading
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AnswersViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(Loading)
    val uiState: StateFlow<UiState> = _uiState

    fun getAnswers(id:String) {
        StackOverflowRetrofitService.api.getAnswers(id).enqueue(
            object : Callback<ApiResponseWrapper<Answer>> {
                override fun onResponse(
                    p0: Call<ApiResponseWrapper<Answer>>,
                    p1: Response<ApiResponseWrapper<Answer>>
                ) {
                    if (p1.isSuccessful) {
                        _uiState.value = p1.body()?.items?.let {
                            UiState.Success(it)
                        } ?: UiState.Error
                    } else {
                        _uiState.value = UiState.Error
                    }
                }

                override fun onFailure(p0: Call<ApiResponseWrapper<Answer>>, p1: Throwable) {
                    _uiState.value = UiState.Error
                }

            }
        )
    }
}