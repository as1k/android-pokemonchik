package com.as1k.pokemonchik.data.di

import com.as1k.pokemonchik.data.mapper.PokemonInfoDTOMapper
import com.as1k.pokemonchik.data.mapper.PokemonResponseDTOMapper
import com.as1k.pokemonchik.data.mapper.RandomQuoteDTOMapper
import org.koin.dsl.module

val dataMapperModule = module {
    factory { PokemonInfoDTOMapper() }
    factory { PokemonResponseDTOMapper() }
    factory { RandomQuoteDTOMapper() }
}
