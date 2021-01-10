package com.as1k.pokemonchik.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.as1k.pokemonchik.domain.repository.PokemonRepository

class PokemonViewModelFactory(
    private val pokemonRepository: PokemonRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(PokemonRepository::class.java)
            .newInstance(pokemonRepository)
    }
}
