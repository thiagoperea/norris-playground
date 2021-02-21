package com.thiagoperea.norrisplayground.presentation.screens.main

import androidx.lifecycle.Observer
import com.thiagoperea.norrisplayground.business.model.JokeItem
import com.thiagoperea.norrisplayground.business.usecase.GetCategoriesUseCase
import com.thiagoperea.norrisplayground.business.usecase.GetJokesUseCase
import com.thiagoperea.norrisplayground.presentation.BaseTest
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifySequence
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest : BaseTest() {

    private data class Fields(
        val getJokesUseCase: GetJokesUseCase,
        val getCategoriesUseCase: GetCategoriesUseCase
    )

    private fun createMocks(): Pair<MainViewModel, Fields> {
        val getJokesUseCase: GetJokesUseCase = mockk(relaxed = true)
        val getCategoriesUseCase: GetCategoriesUseCase = mockk(relaxed = true)
        val fields = Fields(getJokesUseCase, getCategoriesUseCase)

        val viewModel = MainViewModel(fields.getJokesUseCase, fields.getCategoriesUseCase)
        return Pair(viewModel, fields)
    }

    @Test
    fun `test refreshJoke success`() {
        //Arrange
        val (viewModel, fields) = createMocks()
        val observer = mockk<Observer<MainState>>(relaxed = true)
        viewModel.mainState.observeForever(observer)

        coEvery { fields.getJokesUseCase.execute(any()) } returns JokeItem("joke", 1)

        //Act
        viewModel.refreshJoke()

        //Assert
        coVerifySequence {
            observer.onChanged(ofType(MainState.Loading::class))
            fields.getJokesUseCase.execute(null)
            observer.onChanged(ofType(MainState.SuccessJoke::class))
        }
    }

    @Test
    fun `test refreshJoke error`() {
        //Arrange
        val (viewModel, fields) = createMocks()
        val observer = mockk<Observer<MainState>>(relaxed = true)
        viewModel.mainState.observeForever(observer)

        coEvery { fields.getJokesUseCase.execute(any()) } throws RuntimeException()

        //Act
        viewModel.refreshJoke()

        //Assert
        coVerifySequence {
            observer.onChanged(ofType(MainState.Loading::class))
            fields.getJokesUseCase.execute(null)
            observer.onChanged(ofType(MainState.Error::class))
        }
    }

    @Test
    fun `test doOnSuccess`() {
        //Arrange
        val (viewModel, fields) = createMocks()
        val observer = mockk<Observer<MainState>>(relaxed = true)
        viewModel.mainState.observeForever(observer)

        //Act
        viewModel.doOnSuccess(JokeItem("", 1))
        viewModel.doOnSuccess(JokeItem("", 2))
        viewModel.doOnSuccess(JokeItem("", 3))
        viewModel.doOnSuccess(JokeItem("", 4))

        //Assert
        coVerify(exactly = 4) {
            observer.onChanged(ofType(MainState.SuccessJoke::class))
        }
    }

    @Test(expected = UnsupportedOperationException::class)
    fun `test doOnSuccess error`() {
        //Arrange
        val (viewModel, fields) = createMocks()
        val observer = mockk<Observer<MainState>>(relaxed = true)

        //Act
        viewModel.doOnSuccess(JokeItem("", 5))
    }

    @Test
    fun `test getCategories success`() {
        //Arrange
        val (viewModel, fields) = createMocks()
        val observer = mockk<Observer<MainState>>(relaxed = true)
        viewModel.mainState.observeForever(observer)

        coEvery { fields.getCategoriesUseCase.execute() } returns mutableListOf()

        //Act
        viewModel.getCategories()

        //Assert
        coVerifySequence {
            observer.onChanged(ofType(MainState.Loading::class))
            fields.getCategoriesUseCase.execute()
            observer.onChanged(ofType(MainState.SuccessCategories::class))
        }
    }

    @Test
    fun `test getCategories error`() {
        //Arrange
        val (viewModel, fields) = createMocks()
        val observer = mockk<Observer<MainState>>(relaxed = true)
        viewModel.mainState.observeForever(observer)

        coEvery { fields.getCategoriesUseCase.execute() } throws RuntimeException()

        //Act
        viewModel.getCategories()

        //Assert
        coVerifySequence {
            observer.onChanged(ofType(MainState.Loading::class))
            fields.getCategoriesUseCase.execute()
            observer.onChanged(ofType(MainState.Error::class))
        }
    }
}