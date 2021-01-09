package com.as1k.pokemonchik.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TypeItem(
    val slot: Int,
    val type: Type
) : Parcelable
