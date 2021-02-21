package com.thiagoperea.norrisplayground.datasource.repository

import com.thiagoperea.norrisplayground.datasource.service.ChuckNorrisApi

class JokesRepository(
    private val chuckNorrisApi: ChuckNorrisApi
) {

    suspend fun getCategories() = chuckNorrisApi.getCategories()

    suspend fun getRandomJoke() = chuckNorrisApi.getRandomJoke()

    suspend fun getJokeFromCategory(category: String) = chuckNorrisApi.getJokeFromCategory(category)
}