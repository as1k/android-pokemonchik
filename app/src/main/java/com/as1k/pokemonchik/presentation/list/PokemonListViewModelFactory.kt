package com.as1k.pokemonchik.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.as1k.pokemonchik.domain.use_case.PokemonListUseCase

class PokemonListViewModelFactory(
    private val pokemonListUseCase: PokemonListUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(PokemonListUseCase::class.java)
            .newInstance(pokemonListUseCase)
    }
}
