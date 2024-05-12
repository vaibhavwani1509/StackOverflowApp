package com.vaibhavwani.stackoverflowapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.vaibhavwani.stackoverflowapp.model.ApiResponseWrapper
import com.vaibhavwani.stackoverflowapp.model.Question
import com.vaibhavwani.stackoverflowapp.model.network.StackOverflowRetrofitService
import com.vaibhavwani.stackoverflowapp.viewmodel.UiState.Loading
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuestionsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(Loading)
    val uiState: StateFlow<UiState> = _uiState

    var currentPage = 0

    fun getQuestions() {
        StackOverflowRetrofitService.api.getQuestions().enqueue(
            object : Callback<ApiResponseWrapper<Question>> {
                override fun onResponse(
                    p0: Call<ApiResponseWrapper<Question>>,
                    p1: Response<ApiResponseWrapper<Question>>
                ) {
                    if (p1.isSuccessful) {
                        Log.d("VAIBHAV", "QuestionsViewModel.kt :onResponse: 28")
                        _uiState.value = p1.body()?.items?.let {
                            Log.d("VAIBHAV", "QuestionsViewModel.kt :onResponse: 30")
                            currentPage += 1
                            if (_uiState.value is UiState.Success<*>) {
                                val prev = (_uiState.value as UiState.Success<Question>).data.toMutableList()
                                _uiState.value = UiState.Success(prev + it)
                            }
                            UiState.Success(it)
                        } ?: UiState.Error
                    } else {
                        Log.d("VAIBHAV", "QuestionsViewModel.kt :onResponse: 32")
                        _uiState.value = UiState.Error
                    }
                }

                override fun onFailure(p0: Call<ApiResponseWrapper<Question>>, p1: Throwable) {
                    Log.d("VAIBHAV", "QuestionsViewModel.kt :onFailure: 37 " + p1)
                    _uiState.value = UiState.Error
                }

            }
        )
    }

    fun loadMore() {
        getQuestions()
    }
}

sealed class UiState {
    object Loading : UiState()
    class Success<T>(val data: List<T>) : UiState()
    object Error : UiState()
}