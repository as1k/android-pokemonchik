package com.as1k.pokemonchik.data.di

import com.as1k.pokemonchik.BuildConfig
import com.as1k.pokemonchik.data.network.PokemonApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val TIMEOUT = 30L

val networkModule = module {
    single { createPokemonApi() }
}

private fun createPokemonApi(): PokemonApi {
    val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.POKEMON_API_URL)
        .client(createOkHttp())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        return retrofit.create(PokemonApi::class.java)
}

private fun createOkHttp(): OkHttpClient {
    val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(TIMEOUT, TimeUnit.SECONDS)
        .addInterceptor(createHttpLoggingInterceptor())
    return okHttpClient.build()
}

private fun createHttpLoggingInterceptor(): HttpLoggingInterceptor {
    val interceptor = HttpLoggingInterceptor()
    interceptor.level = HttpLoggingInterceptor.Level.BODY
    return interceptor
}
