package com.as1k.pokemonchik.data.di

import com.as1k.pokemonchik.data.repository.PokemonRepositoryImpl
import com.as1k.pokemonchik.data.repository.QuoteRepositoryImpl
import com.as1k.pokemonchik.domain.repository.PokemonRepository
import com.as1k.pokemonchik.domain.repository.QuoteRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<PokemonRepository> { PokemonRepositoryImpl(pokemonApi = get(), pokemonResponseMapper = get(), pokemonInfoMapper = get()) }
    single<QuoteRepository> { QuoteRepositoryImpl(pokemonApi = get(), randomQuoteMapper = get(), randomQuoteDao = get()) }
}
