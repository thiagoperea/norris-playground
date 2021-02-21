package com.thiagoperea.norrisplayground.presentation.screens.main

import android.util.Log
import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thiagoperea.norrisplayground.business.model.JokeItem
import com.thiagoperea.norrisplayground.business.usecase.GetCategoriesUseCase
import com.thiagoperea.norrisplayground.business.usecase.GetJokesUseCase
import com.thiagoperea.norrisplayground.presentation.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val getJokesUseCase: GetJokesUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {

    private val _mainState = MutableLiveData<MainState>()
    val mainState: LiveData<MainState> = _mainState

    var selectedCategory: String? = null

    fun refreshJoke() {
        viewModelScope.launch(Dispatchers.IO) {
            _mainState.postValue(MainState.Loading(R.string.loading_joke))

            try {
                val jokeItem = getJokesUseCase.execute(selectedCategory)
                doOnSuccess(jokeItem)
            } catch (error: Exception) {
                doOnError(error)
            }
        }
    }

    private fun doOnSuccess(jokeItem: JokeItem) {
        Log.d("TESTE_THI", "SUCCESS: $jokeItem")

        jokeItem.image = when (jokeItem.image) {
            0 -> R.raw.a
            1 -> R.raw.b
            2 -> R.raw.c
            3 -> R.raw.d
            4 -> R.raw.e
            else -> throw UnsupportedOperationException()
        }

        _mainState.postValue(MainState.SuccessJokes(jokeItem))
    }

    private fun doOnError(error: Exception) {
        Log.e("TESTE_THI", "ERROR: $error")

        _mainState.postValue(MainState.Error)
    }

    fun getCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            _mainState.postValue(MainState.Loading(R.string.loading_categories))

            val categories = getCategoriesUseCase.execute()
            _mainState.postValue(MainState.SuccessCategories(categories))
        }
    }
}

sealed class MainState {
    data class Loading(@StringRes val message: Int) : MainState()
    data class SuccessJokes(val joke: JokeItem) : MainState()
    data class SuccessCategories(val categories: List<String>) : MainState()
    object Error : MainState()
}