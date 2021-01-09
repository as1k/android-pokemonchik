package com.as1k.pokemonchik.data.mapper

import com.as1k.pokemonchik.data.model.PokemonItemData
import com.as1k.pokemonchik.data.model.PokemonResponseData
import com.as1k.pokemonchik.domain.mapper.Mapper
import com.as1k.pokemonchik.domain.model.PokemonItem
import com.as1k.pokemonchik.domain.model.PokemonResponse

class PokemonResponseMapper :
    Mapper<PokemonResponse, PokemonResponseData> {

    override fun from(model: PokemonResponse): PokemonResponseData = with(model) {
        return PokemonResponseData(
            count = count,
            previous = previous,
            next = next,
            results = results.map { PokemonItemData(it.page, it.name, it.url) }
        )
    }

    override fun to(model: PokemonResponseData): PokemonResponse = with(model) {
        return PokemonResponse(
            count = count,
            previous = previous,
            next = next,
            results = results.map { PokemonItem(it.page, it.name, it.url) }
        )
    }
}
