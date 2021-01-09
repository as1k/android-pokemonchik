package com.as1k.pokemonchik.data.model

import com.google.gson.annotations.SerializedName
import kotlin.random.Random

data class PokemonInfoData(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("height")
    val height: Int,
    @SerializedName("weight")
    val weight: Int,
    @SerializedName("base_experience")
    val experience: Int,
    @SerializedName("types")
    val types: List<TypeItemData>,
    val hp: Int = Random.nextInt(maxHp),
    val attack: Int = Random.nextInt(maxAttack),
    val defense: Int = Random.nextInt(maxDefense),
    val speed: Int = Random.nextInt(maxSpeed),
    val exp: Int = Random.nextInt(maxExp)
) {

    companion object {
        const val maxHp = 300
        const val maxAttack = 300
        const val maxDefense = 300
        const val maxSpeed = 300
        const val maxExp = 1000
    }
}