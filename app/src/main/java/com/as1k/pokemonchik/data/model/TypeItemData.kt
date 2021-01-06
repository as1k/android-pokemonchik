package com.as1k.pokemonchik.data.model

import com.google.gson.annotations.SerializedName

data class TypeItemData(
    @SerializedName("slot")
    val slot: Int,
    @SerializedName("type")
    val type: TypeData
)
