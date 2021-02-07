package com.as1k.pokemonchik.data.model

import com.as1k.pokemonchik.BuildConfig
import com.google.gson.annotations.SerializedName

data class PokemonItemDTO(
    @SerializedName("page")
    var page: Int = 0,
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
) {

    fun getImageUrl(): String {
        val index = url.split("/".toRegex()).dropLast(1).last()
        val imageUrl = BuildConfig.POKEMON_IMAGE_URL
        return "$imageUrl$index.png"
    }
}
