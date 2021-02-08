package com.as1k.pokemonchik.domain.use_case

import com.as1k.pokemonchik.domain.model.PokemonInfo
import com.as1k.pokemonchik.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow

class PokemonDetailsUseCase(
    private val repository: PokemonRepository
) {

    suspend fun getPokemonInfo(name: String): Flow<PokemonInfo> = repository.getPokemonInfo(name)
}
