package com.thiagoperea.norrisplayground.business.usecase

import com.thiagoperea.norrisplayground.business.CoroutineTestRule
import com.thiagoperea.norrisplayground.datasource.model.Joke
import com.thiagoperea.norrisplayground.datasource.repository.JokesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class GetJokesUseCaseTest {

    @get:Rule
    val testRule = CoroutineTestRule()

    @Test
    fun `test execute with no category`() = testRule.dispatcher.runBlockingTest {
        //Arrange
        val repositoryMock = mockk<JokesRepository>(relaxed = true)
        val usecase = GetJokesUseCase(repositoryMock)
        val mockJoke = Joke("id", "description")

        coEvery { repositoryMock.getRandomJoke() } returns mockJoke

        //Act
        usecase.execute(null)
        usecase.execute("")

        //Assert
        coVerify(exactly = 2) { repositoryMock.getRandomJoke() }
    }

    @Test
    fun `test execute with category`() = testRule.dispatcher.runBlockingTest {
        //Arrange
        val repositoryMock = mockk<JokesRepository>(relaxed = true)
        val usecase = GetJokesUseCase(repositoryMock)
        val mockJoke = Joke("id", "description")

        coEvery { repositoryMock.getJokeFromCategory(any()) } returns mockJoke

        //Act
        val response = usecase.execute("category")

        //Assert
        coVerify { repositoryMock.getJokeFromCategory(eq("category")) }

        Assert.assertTrue(response.image in 0..4)
    }
}