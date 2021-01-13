package com.as1k.pokemonchik.domain.use_case

import com.as1k.pokemonchik.domain.model.RandomQuote
import com.as1k.pokemonchik.domain.repository.QuoteRepository

class RandomQuoteUseCase(
    private val repository: QuoteRepository
) {

    fun insertRandomQuote(quote: RandomQuote) = repository.insertRandomQuote(quote)
    fun getQuoteLocal() = repository.getQuoteLocal()
    fun deleteQuote() = repository.deleteQuote()
    fun getRandomQuote() = repository.getRandomQuote()
}
