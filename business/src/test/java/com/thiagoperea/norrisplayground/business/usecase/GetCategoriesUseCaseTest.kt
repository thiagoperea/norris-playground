package com.thiagoperea.norrisplayground.business.usecase

import com.thiagoperea.norrisplayground.business.CoroutineTestRule
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
class GetCategoriesUseCaseTest {

    @get:Rule
    val testRule = CoroutineTestRule()

    @Test
    fun `test execute`() = testRule.dispatcher.runBlockingTest {
        //Arrange
        val repositoryMock = mockk<JokesRepository>(relaxed = true)
        val usecase = GetCategoriesUseCase(repositoryMock)
        val list = mutableListOf("Category A", "Category B")

        coEvery { repositoryMock.getCategories() } returns list

        //Act
        val categories = usecase.execute()

        //Assert
        coVerify { repositoryMock.getCategories() }

        Assert.assertEquals(3, categories.size)
        Assert.assertEquals("random", categories.last())
    }
}