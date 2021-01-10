package com.as1k.pokemonchik.presentation.source

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.as1k.pokemonchik.domain.model.PokemonItem
import com.as1k.pokemonchik.domain.repository.PokemonRepository
import io.reactivex.disposables.CompositeDisposable

class PokemonDataSourceFactory(
    private val repository: PokemonRepository,
    private val compositeDisposable: CompositeDisposable
) : DataSource.Factory<Pair<Int, Int>, PokemonItem>() {

    val pokemonDataSourceLiveData = MutableLiveData<PokemonDataSource>()
    private lateinit var pokemonDataSource: PokemonDataSource

    override fun create(): DataSource<Pair<Int, Int>, PokemonItem> {
        pokemonDataSource = PokemonDataSource(repository, compositeDisposable)
        pokemonDataSourceLiveData.postValue(pokemonDataSource)
        return pokemonDataSource
    }

    fun invalidate() {
        pokemonDataSource.invalidate()
    }
}
