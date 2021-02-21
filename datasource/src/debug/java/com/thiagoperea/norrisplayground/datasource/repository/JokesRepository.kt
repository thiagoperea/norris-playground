package com.thiagoperea.norrisplayground.datasource.repository

import com.thiagoperea.norrisplayground.datasource.model.Joke
import com.thiagoperea.norrisplayground.datasource.service.ChuckNorrisApi

class JokesRepository(
    private val chuckNorrisApi: ChuckNorrisApi
) {

    suspend fun getCategories() = mutableListOf(
        "Category A",
        "Category B",
        "Category C"
    )

    suspend fun getRandomJoke() = Joke("FAKE_ID", "Joke Description")

    suspend fun getJokeFromCategory(category: String) = Joke("FAKE_ID", "Joke from $category")
}