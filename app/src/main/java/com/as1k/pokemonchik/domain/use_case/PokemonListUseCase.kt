package com.as1k.pokemonchik.domain.use_case

import com.as1k.pokemonchik.domain.model.PokemonResponse
import com.as1k.pokemonchik.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow

class PokemonListUseCase(
    private val repository: PokemonRepository
) {

    suspend fun getPokemonList(limit: Int, offset: Int): Flow<PokemonResponse> =
        repository.getPokemonList(limit, offset)
}
