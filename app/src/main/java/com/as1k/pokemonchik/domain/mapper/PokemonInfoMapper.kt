package com.as1k.pokemonchik.domain.mapper

import com.as1k.pokemonchik.data.model.PokemonInfoData
import com.as1k.pokemonchik.data.model.TypeData
import com.as1k.pokemonchik.data.model.TypeItemData
import com.as1k.pokemonchik.domain.model.PokemonInfo
import com.as1k.pokemonchik.domain.model.Type
import com.as1k.pokemonchik.domain.model.TypeItem

class PokemonItemMapper : Mapper<PokemonInfo, PokemonInfoData> {

    override fun from(model: PokemonInfo): PokemonInfoData = with(model) {
        return PokemonInfoData(
            id = id,
            name = name,
            height = height,
            weight = weight,
            experience = experience,
            types = types.map { TypeItemData(it.slot, TypeData(it.type.name)) },
            hp = hp,
            attack = attack,
            defense = defense,
            speed = speed,
            exp = exp
        )
    }

    override fun to(model: PokemonInfoData): PokemonInfo = with(model) {
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
