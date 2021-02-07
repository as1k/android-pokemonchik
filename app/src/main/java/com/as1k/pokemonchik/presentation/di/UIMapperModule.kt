package com.as1k.pokemonchik.presentation.di

import com.as1k.pokemonchik.presentation.mapper.PokemonInfoUIMapper
import com.as1k.pokemonchik.presentation.mapper.PokemonResponseUIMapper
import com.as1k.pokemonchik.presentation.mapper.RandomQuoteUIMapper
import org.koin.dsl.module

val uiMapperModule = module {
    factory { PokemonInfoUIMapper() }
    factory { PokemonResponseUIMapper() }
    factory { RandomQuoteUIMapper() }
}
