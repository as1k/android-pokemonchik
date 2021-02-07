package com.as1k.pokemonchik.data.network

import com.as1k.pokemonchik.BuildConfig
import com.as1k.pokemonchik.data.model.PokemonInfoDTO
import com.as1k.pokemonchik.data.model.PokemonResponseDTO
import com.as1k.pokemonchik.data.model.RandomQuoteEntity
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface PokemonApi {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): PokemonResponseDTO

    @GET("pokemon/{name}")
    suspend fun getPokemonInfo(@Path("name") name: String): PokemonInfoDTO

    @GET
    suspend fun getRandomQuote(@Url url: String = BuildConfig.RANDOM_QUOTE_URL) : RandomQuoteEntity
}
