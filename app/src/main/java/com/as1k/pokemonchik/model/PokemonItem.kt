package com.as1k.pokemonchik.model

import android.os.Parcelable
import com.as1k.pokemonchik.network.NetworkConstants.POKEMON_IMAGE_URL
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PokemonItem(
    @SerializedName("page")
    var page: Int = 0,
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
) : Parcelable {

    fun getImageUrl(): String {
        val index = url.split("/".toRegex()).dropLast(1).last()
        return "$POKEMON_IMAGE_URL$index.png"
    }
}
