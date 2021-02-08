package com.as1k.pokemonchik.presentation.source

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.as1k.pokemonchik.domain.use_case.PokemonListUseCase
import com.as1k.pokemonchik.presentation.mapper.PokemonResponseUIMapper
import com.as1k.pokemonchik.presentation.model.PokemonItemUI
import kotlinx.coroutines.CoroutineScope

class PokemonDataSourceFactory(
    private val pokemonListUseCase: PokemonListUseCase,
    private val pokemonResponseUIMapper: PokemonResponseUIMapper,
    private val viewModelScope: CoroutineScope
) : DataSource.Factory<Pair<Int, Int>, PokemonItemUI>() {

    val pokemonDataSourceLiveData = MutableLiveData<PokemonDataSource>()
    private lateinit var pokemonDataSource: PokemonDataSource

    override fun create(): DataSource<Pair<Int, Int>, PokemonItemUI> {
        pokemonDataSource = PokemonDataSource(pokemonListUseCase, pokemonResponseUIMapper, viewModelScope)
        pokemonDataSourceLiveData.postValue(pokemonDataSource)
        return pokemonDataSource
    }

    fun invalidate() {
        pokemonDataSource.invalidate()
    }
}
