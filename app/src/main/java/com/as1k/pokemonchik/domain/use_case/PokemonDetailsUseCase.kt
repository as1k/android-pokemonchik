package com.as1k.pokemonchik.domain.use_case

import com.as1k.pokemonchik.domain.repository.PokemonRepository

class PokemonDetailsUseCase(
    private val repository: PokemonRepository
) {

    fun getPokemonInfo(name: String) = repository.getPokemonInfo(name)
}
