package com.as1k.pokemonchik.data.mapper

import com.as1k.pokemonchik.data.model.PokemonItemDTO
import com.as1k.pokemonchik.data.model.PokemonResponseDTO
import com.as1k.pokemonchik.domain.mapper.Mapper
import com.as1k.pokemonchik.domain.model.PokemonItem
import com.as1k.pokemonchik.domain.model.PokemonResponse

class PokemonResponseDTOMapper :
    Mapper<PokemonResponse, PokemonResponseDTO> {

    override fun from(model: PokemonResponse): PokemonResponseDTO = with(model) {
        return PokemonResponseDTO(
            count = count,
            previous = previous,
            next = next,
            results = results.map { PokemonItemDTO(it.page, it.name, it.url) }
        )
    }

    override fun to(model: PokemonResponseDTO): PokemonResponse = with(model) {
        return PokemonResponse(
            count = count,
            previous = previous,
            next = next,
            results = results.map { PokemonItem(it.page, it.name, it.getImageUrl()) }
        )
    }
}
