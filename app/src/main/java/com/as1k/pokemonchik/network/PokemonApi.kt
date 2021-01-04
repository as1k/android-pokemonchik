package com.as1k.pokemonchik.network

import com.as1k.pokemonchik.model.PokemonInfo
import com.as1k.pokemonchik.model.PokemonResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0
    ): Response<PokemonResponse>

    @GET("pokemon/{name}")
    suspend fun getPokemonInfo(@Path("name") name: String): Response<PokemonInfo>
}
