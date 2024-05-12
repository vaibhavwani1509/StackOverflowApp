package com.vaibhavwani.stackoverflowapp.model

data class ApiResponseWrapper<T>(
    val items: List<T> = emptyList()
)