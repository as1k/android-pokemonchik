package com.as1k.pokemonchik.presentation.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PokemonItemUI(
    var page: Int = 0,
    val name: String,
    val url: String
) : Parcelable
