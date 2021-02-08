package com.as1k.pokemonchik.domain.mapper

interface Mapper<N, M> {

    fun from(model: N): M

    fun to(model: M): N
}
