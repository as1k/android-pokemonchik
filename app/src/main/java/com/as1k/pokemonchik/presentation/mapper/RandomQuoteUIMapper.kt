package com.as1k.pokemonchik.presentation.mapper

import com.as1k.pokemonchik.domain.mapper.Mapper
import com.as1k.pokemonchik.domain.model.RandomQuote
import com.as1k.pokemonchik.presentation.model.RandomQuoteUI

class RandomQuoteUIMapper :
    Mapper<RandomQuote, RandomQuoteUI> {

    override fun from(model: RandomQuote): RandomQuoteUI = with(model) {
        return RandomQuoteUI(
            quoteText = quoteText,
            quoteAuthor = quoteAuthor
        )
    }

    override fun to(model: RandomQuoteUI): RandomQuote = with(model) {
        return RandomQuote(
            quoteText = quoteText,
            quoteAuthor = quoteAuthor
        )
    }
}
