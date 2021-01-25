package com.as1k.pokemonchik.domain.repository

import com.as1k.pokemonchik.domain.model.RandomQuote
import kotlinx.coroutines.flow.Flow

interface QuoteRepository {

    fun insertRandomQuote(randomQuote: RandomQuote)
    fun getQuoteLocal() : Flow<RandomQuote>
    fun deleteQuote()
    suspend fun getRandomQuote(): Flow<RandomQuote>
}
