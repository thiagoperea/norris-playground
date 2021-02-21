package com.thiagoperea.norrisplayground.presentation

import com.thiagoperea.norrisplayground.presentation.screens.main.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { MainViewModel(get(), get()) }
}