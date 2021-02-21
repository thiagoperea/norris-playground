package com.thiagoperea.norrisplayground.datasource.model

import com.google.gson.annotations.SerializedName

data class Joke(
    val id: String,
    @SerializedName("value") val description: String
)
