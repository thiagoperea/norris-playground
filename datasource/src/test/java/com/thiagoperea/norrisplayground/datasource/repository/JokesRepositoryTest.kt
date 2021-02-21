package com.thiagoperea.norrisplayground.datasource.repository

import com.thiagoperea.norrisplayground.datasource.CoroutineTestRule
import com.thiagoperea.norrisplayground.datasource.service.ChuckNorrisApi
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class JokesRepositoryTest {

    @get:Rule
    val testRule = CoroutineTestRule()

    @Test
    fun `test getCategories`() = testRule.dispatcher.runBlockingTest {
        //Arrange
        val apiMock = mockk<ChuckNorrisApi>(relaxed = true)
        val repository = JokesRepository(apiMock)

        //Act
        repository.getCategories()

        //Assert
        coVerify { apiMock.getCategories() }
    }

    @Test
    fun `test getRandomJoke`() = testRule.dispatcher.runBlockingTest {
        //Arrange
        val apiMock = mockk<ChuckNorrisApi>(relaxed = true)
        val repository = JokesRepository(apiMock)

        //Act
        repository.getRandomJoke()

        //Assert
        coVerify { apiMock.getRandomJoke() }
    }

    @Test
    fun `test getJokeFromCategory`() = testRule.dispatcher.runBlockingTest {
        //Arrange
        val apiMock = mockk<ChuckNorrisApi>(relaxed = true)
        val repository = JokesRepository(apiMock)

        //Act
        repository.getJokeFromCategory("category")

        //Assert
        coVerify { apiMock.getJokeFromCategory(eq("category")) }
    }
}