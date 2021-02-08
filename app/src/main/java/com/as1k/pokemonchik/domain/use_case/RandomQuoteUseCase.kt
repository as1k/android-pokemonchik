package com.as1k.pokemonchik.domain.use_case

import com.as1k.pokemonchik.domain.model.RandomQuote
import com.as1k.pokemonchik.domain.repository.QuoteRepository
import kotlinx.coroutines.flow.Flow

class RandomQuoteUseCase(
    private val repository: QuoteRepository
) {

    fun insertRandomQuote(quote: RandomQuote) = repository.insertRandomQuote(quote)
    fun getQuoteLocal(): Flow<RandomQuote> = repository.getQuoteLocal()
    fun deleteQuote() = repository.deleteQuote()
    suspend fun getRandomQuote(): Flow<RandomQuote> = repository.getRandomQuote()
}
