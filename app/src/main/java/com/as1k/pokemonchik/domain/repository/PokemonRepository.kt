package com.as1k.pokemonchik.domain.repository

import com.as1k.pokemonchik.data.model.PokemonInfoData
import com.as1k.pokemonchik.data.model.PokemonResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonRepository {

    fun getPokemonList(limit: Int, offset: Int): Response<PokemonResponse>
    fun getPokemonInfo(name: String): Response<PokemonInfoData>
}
