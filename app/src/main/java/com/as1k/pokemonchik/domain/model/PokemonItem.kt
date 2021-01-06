package com.as1k.pokemonchik.domain.model

import android.os.Parcelable
import com.as1k.pokemonchik.network.NetworkConstants.POKEMON_IMAGE_URL
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class PokemonItem(
    var page: Int = 0,
    val name: String,
    val url: String
) {

    fun getImageUrl(): String {
        val index = url.split("/".toRegex()).dropLast(1).last()
        return "$POKEMON_IMAGE_URL$index.png"
    }
}
