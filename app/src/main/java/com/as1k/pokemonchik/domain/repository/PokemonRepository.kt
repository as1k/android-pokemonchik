package com.as1k.pokemonchik.domain.repository

import com.as1k.pokemonchik.domain.model.PokemonResponse
import com.as1k.pokemonchik.domain.model.PokemonInfo
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {

    suspend fun getPokemonList(limit: Int, offset: Int): Flow<PokemonResponse>
    suspend fun getPokemonInfo(name: String): Flow<PokemonInfo>
}
