package com.as1k.pokemonchik.presentation.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PokemonResponseUI(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonItemUI>
) : Parcelable
