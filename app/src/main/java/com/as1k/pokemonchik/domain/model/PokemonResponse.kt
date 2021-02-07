package com.as1k.pokemonchik.domain.model

data class PokemonResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonItem>
)
