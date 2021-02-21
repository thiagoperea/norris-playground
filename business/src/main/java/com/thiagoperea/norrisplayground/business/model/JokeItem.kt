package com.thiagoperea.norrisplayground.business.model

import androidx.annotation.RawRes

data class JokeItem(
    val joke: String,
    @RawRes var image: Int
)
