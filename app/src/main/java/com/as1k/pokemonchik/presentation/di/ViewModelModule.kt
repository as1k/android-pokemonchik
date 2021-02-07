package com.as1k.pokemonchik.presentation.di

import com.as1k.pokemonchik.presentation.details.PokemonDetailsViewModel
import com.as1k.pokemonchik.presentation.details.RandomQuoteViewModel
import com.as1k.pokemonchik.presentation.list.PokemonListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule =  module {
    viewModel { PokemonListViewModel(pokemonListUseCase = get()) }
    viewModel { PokemonDetailsViewModel(pokemonDetailsUseCase = get()) }
    viewModel { RandomQuoteViewModel(randomQuoteUseCase = get()) }
}
