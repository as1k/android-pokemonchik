package com.as1k.pokemonchik.presentation.mapper

import com.as1k.pokemonchik.domain.mapper.Mapper
import com.as1k.pokemonchik.domain.model.PokemonItem
import com.as1k.pokemonchik.domain.model.PokemonResponse
import com.as1k.pokemonchik.presentation.model.PokemonItemUI
import com.as1k.pokemonchik.presentation.model.PokemonResponseUI

class PokemonResponseUIMapper :
    Mapper<PokemonResponse, PokemonResponseUI> {

    override fun from(model: PokemonResponse): PokemonResponseUI = with(model) {
        return PokemonResponseUI(
            count = count,
            previous = previous,
            next = next,
            results = results.map { PokemonItemUI(it.page, it.name, it.url) }
        )
    }

    override fun to(model: PokemonResponseUI): PokemonResponse = with(model) {
        return PokemonResponse(
            count = count,
            previous = previous,
            next = next,
            results = results.map { PokemonItem(it.page, it.name, it.url) }
        )
    }
}
