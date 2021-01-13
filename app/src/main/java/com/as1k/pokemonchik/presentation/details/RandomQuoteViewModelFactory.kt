package com.as1k.pokemonchik.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.as1k.pokemonchik.domain.use_case.RandomQuoteUseCase

class RandomQuoteViewModelFactory(
    private val randomQuoteUseCase: RandomQuoteUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(RandomQuoteUseCase::class.java)
            .newInstance(randomQuoteUseCase)
    }
}
