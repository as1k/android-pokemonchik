package com.as1k.pokemonchik.data.model

import com.google.gson.annotations.SerializedName
import kotlin.random.Random

data class PokemonInfoDTO(
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
    val types: List<TypeItemDTO>,
    val hp: Int = 0,
    val attack: Int = 0,
    val defense: Int = 0,
    val speed: Int = 0,
    val exp: Int = 0
) {

    fun getHpInt(): Int = Random.nextInt(maxHp)
    fun getAttackInt(): Int = Random.nextInt(maxAttack)
    fun getDefenseInt(): Int = Random.nextInt(maxDefense)
    fun getSpeedInt(): Int = Random.nextInt(maxSpeed)
    fun getExpInt(): Int = Random.nextInt(maxExp)

    companion object {
        const val maxHp = 300
        const val maxAttack = 300
        const val maxDefense = 300
        const val maxSpeed = 300
        const val maxExp = 1000
    }
}
