package com.as1k.pokemonchik.data.repository

import com.as1k.pokemonchik.data.database.RandomQuoteDao
import com.as1k.pokemonchik.data.mapper.RandomQuoteMapper
import com.as1k.pokemonchik.data.network.PokemonApi
import com.as1k.pokemonchik.domain.model.RandomQuote
import com.as1k.pokemonchik.domain.repository.QuoteRepository
import io.reactivex.Flowable
import io.reactivex.Single

class QuoteRepositoryImpl(
    private val pokemonApi: PokemonApi,
    private val randomQuoteDao: RandomQuoteDao,
    private val randomQuoteMapper: RandomQuoteMapper
) : QuoteRepository {

    override fun insertRandomQuote(randomQuote: RandomQuote) {
        randomQuoteDao.insertRandomQuote(randomQuoteMapper.from(randomQuote))
    }

    override fun getQuoteLocal(): Flowable<RandomQuote> {
        return randomQuoteDao.getQuote()
            .map { quoteData -> randomQuoteMapper.to(quoteData) }
    }

    override fun deleteQuote() {
        randomQuoteDao.deleteQuote()
    }

    override fun getRandomQuote(): Single<RandomQuote> {
        return pokemonApi.getRandomQuote()
            .flatMap { response ->
                if (response.isSuccessful) {
                    Single.just(response.body())
                } else {
                    Single.error(Throwable("error random quote"))
                }
            }
            .map { quoteData ->
                randomQuoteMapper.to(quoteData)
            }
    }
}
