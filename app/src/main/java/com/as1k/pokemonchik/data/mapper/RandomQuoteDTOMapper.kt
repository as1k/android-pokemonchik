package com.as1k.pokemonchik.data.mapper

import com.as1k.pokemonchik.data.model.RandomQuoteEntity
import com.as1k.pokemonchik.domain.mapper.Mapper
import com.as1k.pokemonchik.domain.model.RandomQuote

class RandomQuoteDTOMapper :
    Mapper<RandomQuote, RandomQuoteEntity> {

    override fun from(model: RandomQuote): RandomQuoteEntity = with(model) {
        return RandomQuoteEntity(
            quoteText = quoteText,
            quoteAuthor = quoteAuthor
        )
    }

    override fun to(model: RandomQuoteEntity): RandomQuote = with(model) {
        return RandomQuote(
            quoteText = quoteText,
            quoteAuthor = quoteAuthor
        )
    }
}
