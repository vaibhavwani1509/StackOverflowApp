package com.vaibhavwani.stackoverflowapp.model

import com.google.gson.annotations.SerializedName

data class Answer(
    @SerializedName("answer_id")
    val answerId: String?
)