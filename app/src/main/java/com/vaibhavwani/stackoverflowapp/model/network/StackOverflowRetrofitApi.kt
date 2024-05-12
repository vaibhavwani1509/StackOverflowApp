package com.vaibhavwani.stackoverflowapp.model.network

import com.vaibhavwani.stackoverflowapp.model.Answer
import com.vaibhavwani.stackoverflowapp.model.ApiResponseWrapper
import com.vaibhavwani.stackoverflowapp.model.Question
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StackOverflowRetrofitApi {

    @GET("/2.3/questions?pagesize=2&order=desc&sort=activity&tagged=android&site=stackoverflow")
    fun getQuestions(
        @Query("page") page: Int
    ): Call<ApiResponseWrapper<Question>>

    @GET("/2.3/questions/{id}/answers?&page=1&pagesize=2&order=desc&sort=activity&site=stackoverflow")
    fun getAnswers(
        @Path("id") id: String
    ): Call<ApiResponseWrapper<Answer>>
}