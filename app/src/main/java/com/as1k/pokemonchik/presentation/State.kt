package com.as1k.pokemonchik.presentation

import com.as1k.pokemonchik.presentation.model.PokemonInfoUI
import com.as1k.pokemonchik.presentation.model.PokemonResponseUI
import com.as1k.pokemonchik.presentation.model.RandomQuoteUI

sealed class PokemonState {
    object ShowLoading : PokemonState()
    object HideLoading : PokemonState()
    data class ResultListResponse(val pokemonListResponse: PokemonResponseUI) : PokemonState()
    data class ResultItem(val pokemonDetails: PokemonInfoUI) : PokemonState()
    data class Error(val error: String?) : PokemonState()
}

sealed class QuoteState {
    object ShowLoading : QuoteState()
    object HideLoading : QuoteState()
    data class ResultItem(val randomQuoteEntity: RandomQuoteUI) : QuoteState()
    data class Error(val error: String?) : QuoteState()
}
