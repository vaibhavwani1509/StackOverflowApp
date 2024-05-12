package com.vaibhavwani.stackoverflowapp.model.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
object StackOverflowRetrofitService {

    private const val BASE_URL = "https://api.stackexchange.com/"

    val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(StackOverflowRetrofitApi::class.java)
}