package com.vaibhavwani.stackoverflowapp.model

import com.google.gson.annotations.SerializedName

data class Question(
    @SerializedName("question_id")
    val id: String? = null,
    val title: String? = null,
    val score: Int = 0,
    @SerializedName("creation_date")
    val date: String? = null,
)