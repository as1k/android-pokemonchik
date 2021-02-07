package com.as1k.pokemonchik.data.di

import com.as1k.pokemonchik.data.repository.PokemonRepositoryImpl
import com.as1k.pokemonchik.data.repository.QuoteRepositoryImpl
import com.as1k.pokemonchik.domain.repository.PokemonRepository
import com.as1k.pokemonchik.domain.repository.QuoteRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<PokemonRepository> { PokemonRepositoryImpl(pokemonApi = get(), pokemonResponseDTOMapper = get(), pokemonInfoDTOMapper = get()) }
    single<QuoteRepository> { QuoteRepositoryImpl(pokemonApi = get(), randomQuoteDTOMapper = get(), randomQuoteDao = get()) }
}
