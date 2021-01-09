package com.as1k.pokemonchik.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Type(
    val name: String
) : Parcelable
