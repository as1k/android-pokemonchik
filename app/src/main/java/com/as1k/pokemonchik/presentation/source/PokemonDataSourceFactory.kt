package com.as1k.pokemonchik.presentation.source

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.as1k.pokemonchik.domain.model.PokemonItem
import com.as1k.pokemonchik.domain.use_case.PokemonListUseCase
import kotlinx.coroutines.CoroutineScope

class PokemonDataSourceFactory(
    private val pokemonListUseCase: PokemonListUseCase,
    private val viewModelScope: CoroutineScope
) : DataSource.Factory<Pair<Int, Int>, PokemonItem>() {

    val pokemonDataSourceLiveData = MutableLiveData<PokemonDataSource>()
    private lateinit var pokemonDataSource: PokemonDataSource

    override fun create(): DataSource<Pair<Int, Int>, PokemonItem> {
        pokemonDataSource = PokemonDataSource(pokemonListUseCase, viewModelScope)
        pokemonDataSourceLiveData.postValue(pokemonDataSource)
        return pokemonDataSource
    }

    fun invalidate() {
        pokemonDataSource.invalidate()
    }
}
