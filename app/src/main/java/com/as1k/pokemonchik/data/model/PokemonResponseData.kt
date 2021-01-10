package com.as1k.pokemonchik.data.model

import com.google.gson.annotations.SerializedName

data class PokemonResponseData(
    @SerializedName("count")
    val count: Int,
    @SerializedName("next")
    val next: String?,
    @SerializedName("previous")
    val previous: String?,
    @SerializedName("results")
    val results: List<PokemonItemData>
)
