package com.as1k.pokemonchik.data.di

import com.as1k.pokemonchik.data.mapper.PokemonInfoMapper
import com.as1k.pokemonchik.data.mapper.PokemonResponseMapper
import com.as1k.pokemonchik.data.mapper.RandomQuoteMapper
import org.koin.dsl.module

val dataMapperModule = module {
    factory { PokemonInfoMapper() }
    factory { PokemonResponseMapper() }
    factory { RandomQuoteMapper() }
}
