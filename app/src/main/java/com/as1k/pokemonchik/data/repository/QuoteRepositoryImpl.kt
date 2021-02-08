package com.as1k.pokemonchik.data.repository

import com.as1k.pokemonchik.data.database.RandomQuoteDao
import com.as1k.pokemonchik.data.mapper.RandomQuoteDTOMapper
import com.as1k.pokemonchik.data.network.PokemonApi
import com.as1k.pokemonchik.domain.model.RandomQuote
import com.as1k.pokemonchik.domain.repository.QuoteRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map

class QuoteRepositoryImpl(
    private val pokemonApi: PokemonApi,
    private val randomQuoteDao: RandomQuoteDao,
    private val randomQuoteDTOMapper: RandomQuoteDTOMapper
) : QuoteRepository {

    @ExperimentalCoroutinesApi
    private val randomQuoteChannel = ConflatedBroadcastChannel<RandomQuote>()

    override fun insertRandomQuote(randomQuote: RandomQuote) {
        randomQuoteDao.insertRandomQuote(randomQuoteDTOMapper.from(randomQuote))
    }

    override fun getQuoteLocal(): Flow<RandomQuote> {
        return randomQuoteDao.getQuote()
            .map { quoteData -> randomQuoteDTOMapper.to(quoteData) }
    }

    override fun deleteQuote() {
        randomQuoteDao.deleteQuote()
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    override suspend fun getRandomQuote(): Flow<RandomQuote> {
        val quoteData = pokemonApi.getRandomQuote()
        val quote = randomQuoteDTOMapper.to(quoteData)
        randomQuoteChannel.offer(quote)
        return randomQuoteChannel.asFlow()
    }
}
