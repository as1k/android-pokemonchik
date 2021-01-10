package com.as1k.pokemonchik.presentation

import com.as1k.pokemonchik.domain.model.PokemonInfo
import com.as1k.pokemonchik.domain.model.PokemonResponse

sealed class PokemonState {
    object ShowLoading : PokemonState()
    object HideLoading : PokemonState()
    data class ResultListResponse(val pokemonListResponse: PokemonResponse) : PokemonState()
    data class ResultItem(val pokemonDetails: PokemonInfo) : PokemonState()
    data class Error(val error: String?) : PokemonState()
}
