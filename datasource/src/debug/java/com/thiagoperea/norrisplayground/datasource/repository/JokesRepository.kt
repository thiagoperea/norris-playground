package com.thiagoperea.norrisplayground.datasource.repository

import com.thiagoperea.norrisplayground.datasource.model.Joke
import com.thiagoperea.norrisplayground.datasource.service.ChuckNorrisApi

class JokesRepository(
    private val chuckNorrisApi: ChuckNorrisApi
) {

    suspend fun getCategories(): List<String> {
        return listOf()
    }

    fun getRandomJoke(): Joke {
        return Joke()
    }

    fun getJokeFromCategory(category: String): Joke {
        return Joke()
    }
}