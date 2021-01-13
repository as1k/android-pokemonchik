package com.as1k.pokemonchik.domain.model

import android.os.Parcelable
import com.as1k.pokemonchik.BuildConfig
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PokemonItem(
    var page: Int = 0,
    val name: String,
    val url: String
) : Parcelable {

    fun getImageUrl(): String {
        val index = url.split("/".toRegex()).dropLast(1).last()
        val imageUrl = BuildConfig.POKEMON_IMAGE_URL
        return "$imageUrl$index.png"
    }
}
