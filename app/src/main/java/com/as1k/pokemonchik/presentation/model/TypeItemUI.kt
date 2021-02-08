package com.as1k.pokemonchik.presentation.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TypeItemUI(
    val slot: Int,
    val type: TypeUI
) : Parcelable
