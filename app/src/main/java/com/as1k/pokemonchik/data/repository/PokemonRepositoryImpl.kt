package com.as1k.pokemonchik.data.repository

import com.as1k.pokemonchik.data.mapper.PokemonInfoMapper
import com.as1k.pokemonchik.data.mapper.PokemonResponseMapper
import com.as1k.pokemonchik.data.network.PokemonApi
import com.as1k.pokemonchik.domain.model.PokemonInfo
import com.as1k.pokemonchik.domain.model.PokemonResponse
import com.as1k.pokemonchik.domain.repository.PokemonRepository
import io.reactivex.Single

class PokemonRepositoryImpl(
    private val pokemonApi: PokemonApi,
    private val pokemonResponseMapper: PokemonResponseMapper,
    private val pokemonInfoMapper: PokemonInfoMapper
) : PokemonRepository {

    override fun getPokemonList(limit: Int, offset: Int): Single<PokemonResponse> {
        return pokemonApi.getPokemonList(limit, offset)
            .flatMap { response ->
                if (response.isSuccessful) {
                    Single.just(response.body())
                } else {
                    Single.error(Throwable("error pokemon list"))
                }
            }.map { pokemonResponseData ->
                pokemonResponseMapper.to(pokemonResponseData)
            }
    }

    override fun getPokemonInfo(name: String): Single<PokemonInfo> {
        return pokemonApi.getPokemonInfo(name)
            .flatMap { response ->
                if (response.isSuccessful) {
                    Single.just(response.body())
                } else {
                    Single.error(Throwable("error pokemon info"))
                }
            }
            .map { pokemonInfoData ->
                pokemonInfoMapper.to(pokemonInfoData)
            }
    }
}
