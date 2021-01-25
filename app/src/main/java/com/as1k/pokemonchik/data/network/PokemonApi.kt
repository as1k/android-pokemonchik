package com.as1k.pokemonchik.data.network

import com.as1k.pokemonchik.BuildConfig
import com.as1k.pokemonchik.data.model.PokemonInfoData
import com.as1k.pokemonchik.data.model.PokemonResponseData
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
    ): PokemonResponseData

    @GET("pokemon/{name}")
    suspend fun getPokemonInfo(@Path("name") name: String): PokemonInfoData

    @GET
    suspend fun getRandomQuote(@Url url: String = BuildConfig.RANDOM_QUOTE_URL) : RandomQuoteEntity
}
