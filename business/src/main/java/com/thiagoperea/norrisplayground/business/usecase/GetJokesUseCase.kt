package com.thiagoperea.norrisplayground.business.usecase

import com.thiagoperea.norrisplayground.business.model.JokeItem
import com.thiagoperea.norrisplayground.datasource.repository.JokesRepository
import kotlin.random.Random

class GetJokesUseCase(
    private val jokesRepository: JokesRepository
) {

    suspend fun execute(category: String?): JokeItem {
        val joke = if (category.isNullOrEmpty() || category == "random") {
            jokesRepository.getRandomJoke()
        } else {
            jokesRepository.getJokeFromCategory(category)
        }

        val image = Random.nextInt(0, 4)
        return JokeItem(joke.description, image)
    }
}
