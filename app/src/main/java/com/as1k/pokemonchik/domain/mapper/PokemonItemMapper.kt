package com.as1k.pokemonchik.domain.mapper

import com.as1k.pokemonchik.data.model.PokemonItemData
import com.as1k.pokemonchik.domain.model.PokemonItem

class PokemonItemMapper : Mapper<PokemonItem, PokemonItemData> {

    override fun from(model: PokemonItem): PokemonItemData = with(model) {
        return PokemonItemData(
            page = page,
            name = name,
            url = url
        )
    }

    override fun to(model: PokemonItemData): PokemonItem = with(model) {
        return PokemonItem(
            page = page,
            name = name,
            url = url
        )
    }
}
