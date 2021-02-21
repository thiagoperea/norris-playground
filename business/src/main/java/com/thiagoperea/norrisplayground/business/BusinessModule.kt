package com.thiagoperea.norrisplayground.business

import com.thiagoperea.norrisplayground.business.usecase.GetJokesUseCase
import org.koin.dsl.module

val businessModule = module {
    factory { GetJokesUseCase(get()) }
}