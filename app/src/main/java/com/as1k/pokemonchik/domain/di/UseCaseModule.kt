package com.as1k.pokemonchik.domain.di

import com.as1k.pokemonchik.domain.use_case.PokemonDetailsUseCase
import com.as1k.pokemonchik.domain.use_case.PokemonListUseCase
import com.as1k.pokemonchik.domain.use_case.RandomQuoteUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { PokemonDetailsUseCase(repository = get()) }
    factory { PokemonListUseCase(repository = get()) }
    factory { RandomQuoteUseCase(repository = get()) }
}
