package com.as1k.pokemonchik.data.network

import com.as1k.pokemonchik.BuildConfig
import com.as1k.pokemonchik.data.model.PokemonInfoData
import com.as1k.pokemonchik.data.model.PokemonResponseData
import com.as1k.pokemonchik.data.model.RandomQuoteEntity
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface PokemonApi {

    @GET("pokemon")
    fun getPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Single<Response<PokemonResponseData>>

    @GET("pokemon/{name}")
    fun getPokemonInfo(@Path("name") name: String): Single<Response<PokemonInfoData>>

    @GET
    fun getRandomQuote(@Url url: String = BuildConfig.RANDOM_QUOTE_URL) : Single<Response<RandomQuoteEntity>>
}
