package com.as1k.pokemonchik.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quote")
data class RandomQuoteEntity(
    @PrimaryKey
    val quoteText: String,
    val quoteAuthor: String
)
