package com.as1k.pokemonchik.domain.repository

import com.as1k.pokemonchik.domain.model.PokemonResponse
import com.as1k.pokemonchik.domain.model.PokemonInfo
import io.reactivex.Single

interface PokemonRepository {

    fun getPokemonList(limit: Int, offset: Int): Single<PokemonResponse>
    fun getPokemonInfo(name: String): Single<PokemonInfo>
}
