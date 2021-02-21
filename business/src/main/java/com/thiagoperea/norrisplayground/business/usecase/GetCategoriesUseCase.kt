package com.thiagoperea.norrisplayground.business.usecase

import com.thiagoperea.norrisplayground.datasource.repository.JokesRepository

class GetCategoriesUseCase(
    private val jokesRepository: JokesRepository
) {

    suspend fun execute() = jokesRepository.getCategories()
}
