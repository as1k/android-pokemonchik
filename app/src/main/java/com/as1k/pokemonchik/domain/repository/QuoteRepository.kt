package com.as1k.pokemonchik.domain.repository

import com.as1k.pokemonchik.domain.model.RandomQuote
import io.reactivex.Flowable
import io.reactivex.Single

interface QuoteRepository {

    fun insertRandomQuote(randomQuote: RandomQuote)
    fun getQuoteLocal() : Flowable<RandomQuote>
    fun deleteQuote()
    fun getRandomQuote(): Single<RandomQuote>
}
