package com.as1k.pokemonchik.data.model

import com.google.gson.annotations.SerializedName

data class PokemonItemData(
    @SerializedName("page")
    var page: Int = 0,
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)
