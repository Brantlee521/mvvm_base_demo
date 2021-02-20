package com.example.demomvvm

import com.google.gson.annotations.SerializedName

data class FAQ(
    @SerializedName("question")
    val question: String = "",
    @SerializedName("answer")
    val answer: String = "",
    @SerializedName("id")
    val id: Int = 0
)