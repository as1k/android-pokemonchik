package com.as1k.pokemonchik.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.as1k.pokemonchik.domain.use_case.PokemonDetailsUseCase

class PokemonDetailsViewModelFactory(
    private val detailsUseCase: PokemonDetailsUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(PokemonDetailsUseCase::class.java)
            .newInstance(detailsUseCase)
    }
}
