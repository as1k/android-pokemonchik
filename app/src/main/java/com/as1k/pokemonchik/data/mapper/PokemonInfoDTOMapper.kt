package com.as1k.pokemonchik.data.mapper

import com.as1k.pokemonchik.data.model.PokemonInfoDTO
import com.as1k.pokemonchik.data.model.TypeDTO
import com.as1k.pokemonchik.data.model.TypeItemDTO
import com.as1k.pokemonchik.domain.mapper.Mapper
import com.as1k.pokemonchik.domain.model.PokemonInfo
import com.as1k.pokemonchik.domain.model.Type
import com.as1k.pokemonchik.domain.model.TypeItem

class PokemonInfoDTOMapper : Mapper<PokemonInfo, PokemonInfoDTO> {

    override fun from(model: PokemonInfo): PokemonInfoDTO = with(model) {
        return PokemonInfoDTO(
            id = id,
            name = name,
            height = height,
            weight = weight,
            experience = experience,
            types = types.map { TypeItemDTO(it.slot, TypeDTO(it.type.name)) },
            hp = hp,
            attack = attack,
            defense = defense,
            speed = speed,
            exp = exp
        )
    }

    override fun to(model: PokemonInfoDTO): PokemonInfo = with(model) {
        return PokemonInfo(
            id = id,
            name = name,
            height = height,
            weight = weight,
            experience = experience,
            types = types.map { TypeItem(it.slot, Type(it.type.name)) },
            hp = this.getHpInt(),
            attack = this.getAttackInt(),
            defense = this.getDefenseInt(),
            speed = this.getSpeedInt(),
            exp = this.getExpInt()
        )
    }
}
