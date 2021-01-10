package com.as1k.pokemonchik.domain.use_case

import com.as1k.pokemonchik.domain.repository.PokemonRepository

class PokemonListUseCase(
    private val repository: PokemonRepository
) {

    fun getPokemonList(limit: Int, offset: Int) = repository.getPokemonList(limit, offset)
}
