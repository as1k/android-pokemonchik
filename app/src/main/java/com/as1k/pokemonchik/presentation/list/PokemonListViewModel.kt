package com.as1k.pokemonchik.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.as1k.pokemonchik.domain.model.PokemonItem
import com.as1k.pokemonchik.domain.repository.PokemonRepository
import com.as1k.pokemonchik.presentation.PokemonState
import com.as1k.pokemonchik.presentation.base.BaseViewModel
import com.as1k.pokemonchik.presentation.source.PokemonDataSource
import com.as1k.pokemonchik.presentation.source.PokemonDataSourceFactory

class PokemonListViewModel(
    pokemonRepository: PokemonRepository
) : BaseViewModel() {

    val pagedListLiveData: LiveData<PagedList<PokemonItem>>
    val liveData: LiveData<PokemonState>

    private val pokemonDataSourceFactory: PokemonDataSourceFactory

    init {
        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(PokemonDataSource.DEFAULT_LIMIT * 2)
            .setPageSize(PokemonDataSource.DEFAULT_LIMIT)
            .setPrefetchDistance(10)
            .build()
        pokemonDataSourceFactory = PokemonDataSourceFactory(pokemonRepository, compositeDisposable)

        pagedListLiveData = LivePagedListBuilder(pokemonDataSourceFactory, pagedListConfig)
            .build()

        liveData = Transformations.switchMap(
            pokemonDataSourceFactory.pokemonDataSourceLiveData,
            PokemonDataSource::getStateMutableLiveData
        )
    }

    fun clear() = pokemonDataSourceFactory.invalidate()
}
