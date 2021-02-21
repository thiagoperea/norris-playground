package com.thiagoperea.norrisplayground.datasource

import com.thiagoperea.norrisplayground.datasource.repository.JokesRepository
import com.thiagoperea.norrisplayground.datasource.service.ChuckNorrisApi
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val datasourceModule = module {
    single { JokesRepository() }

    single {
        Retrofit.Builder()
            .baseUrl("https://api.chucknorris.io/jokes/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single { get<Retrofit>().create(ChuckNorrisApi::class.java) }
}