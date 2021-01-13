package com.as1k.pokemonchik.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quote")
data class RandomQuote(
    @PrimaryKey
    val quoteText: String,
    val quoteAuthor: String
)
