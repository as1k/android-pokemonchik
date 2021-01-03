package com.as1k.pokemonchik.network

import com.as1k.pokemonchik.model.PokemonInfo
import com.as1k.pokemonchik.model.PokemonResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {

    @GET
    suspend fun getPokemonList(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0
    ): Flow<Response<PokemonResponse>>

    @GET("pokemon/{name}")
    suspend fun getPokemonInfo(@Path("name") name: String): Flow<Response<PokemonInfo>>
}
