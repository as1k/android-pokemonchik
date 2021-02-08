package com.as1k.pokemonchik.presentation.mapper

import com.as1k.pokemonchik.domain.mapper.Mapper
import com.as1k.pokemonchik.domain.model.PokemonInfo
import com.as1k.pokemonchik.domain.model.Type
import com.as1k.pokemonchik.domain.model.TypeItem
import com.as1k.pokemonchik.presentation.model.PokemonInfoUI
import com.as1k.pokemonchik.presentation.model.TypeItemUI
import com.as1k.pokemonchik.presentation.model.TypeUI

class PokemonInfoUIMapper : Mapper<PokemonInfo, PokemonInfoUI> {

    override fun from(model: PokemonInfo): PokemonInfoUI = with(model) {
        return PokemonInfoUI(
            id = id,
            name = name,
            height = height,
            weight = weight,
            experience = experience,
            types = types.map { TypeItemUI(it.slot, TypeUI(it.type.name)) },
            hp = hp,
            attack = attack,
            defense = defense,
            speed = speed,
            exp = exp
        )
    }

    override fun to(model: PokemonInfoUI): PokemonInfo = with(model) {
        return PokemonInfo(
            id = id,
            name = name,
            height = height,
            weight = weight,
            experience = experience,
            types = types.map { TypeItem(it.slot, Type(it.type.name)) },
            hp = hp,
            attack = attack,
            defense = defense,
            speed = speed,
            exp = exp
        )
    }
}
