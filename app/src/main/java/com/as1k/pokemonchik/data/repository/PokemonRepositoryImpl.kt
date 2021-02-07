package com.as1k.pokemonchik.data.repository

import com.as1k.pokemonchik.data.mapper.PokemonInfoDTOMapper
import com.as1k.pokemonchik.data.mapper.PokemonResponseDTOMapper
import com.as1k.pokemonchik.data.network.PokemonApi
import com.as1k.pokemonchik.domain.model.PokemonInfo
import com.as1k.pokemonchik.domain.model.PokemonResponse
import com.as1k.pokemonchik.domain.repository.PokemonRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

class PokemonRepositoryImpl(
    private val pokemonApi: PokemonApi,
    private val pokemonResponseDTOMapper: PokemonResponseDTOMapper,
    private val pokemonInfoDTOMapper: PokemonInfoDTOMapper
) : PokemonRepository {

    @ExperimentalCoroutinesApi
    private val pokemonResponseChannel = ConflatedBroadcastChannel<PokemonResponse>()
    @ExperimentalCoroutinesApi
    private val pokemonInfoChannel = ConflatedBroadcastChannel<PokemonInfo>()

    @FlowPreview
    @ExperimentalCoroutinesApi
    override suspend fun getPokemonList(limit: Int, offset: Int): Flow<PokemonResponse> {
        val pokemonResponseData = pokemonApi.getPokemonList(limit, offset)
        val pokemonResponse = pokemonResponseDTOMapper.to(pokemonResponseData)
        pokemonResponseChannel.offer(pokemonResponse)
        return pokemonResponseChannel.asFlow()
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    override suspend fun getPokemonInfo(name: String): Flow<PokemonInfo> {
        val pokemonInfoData = pokemonApi.getPokemonInfo(name)
        val pokemonInfo = pokemonInfoDTOMapper.to(pokemonInfoData)
        pokemonInfoChannel.offer(pokemonInfo)
        return pokemonInfoChannel.asFlow()
    }
}
