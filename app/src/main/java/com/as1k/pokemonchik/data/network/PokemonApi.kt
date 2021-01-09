package com.as1k.pokemonchik.data.network

import com.as1k.pokemonchik.data.model.PokemonInfoData
import com.as1k.pokemonchik.data.model.PokemonResponseData
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {

    @GET("pokemon")
    fun getPokemonList(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0
    ): Single<Response<PokemonResponseData>>

    @GET("pokemon/{name}")
    fun getPokemonInfo(@Path("name") name: String): Single<Response<PokemonInfoData>>
}
