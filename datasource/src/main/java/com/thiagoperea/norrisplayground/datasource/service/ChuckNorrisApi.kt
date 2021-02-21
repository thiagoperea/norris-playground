package com.thiagoperea.norrisplayground.datasource.service

import com.thiagoperea.norrisplayground.datasource.model.Joke
import retrofit2.http.GET
import retrofit2.http.Query

interface ChuckNorrisApi {

    @GET("random")
    suspend fun getRandomJoke(): Joke

    @GET("random")
    suspend fun getJokeFromCategory(@Query("category") category: String): Joke

    @GET("categories")
    suspend fun getCategories(): MutableList<String>
}