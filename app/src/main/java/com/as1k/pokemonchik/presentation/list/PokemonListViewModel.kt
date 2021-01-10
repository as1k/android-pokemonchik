package com.as1k.pokemonchik.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.as1k.pokemonchik.domain.repository.PokemonRepository
import com.as1k.pokemonchik.presentation.PokemonState
import com.as1k.pokemonchik.presentation.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PokemonListViewModel(
    private val pokemonRepository: PokemonRepository
) : BaseViewModel() {

    private val state = MutableLiveData<PokemonState>()
    val liveData: LiveData<PokemonState> = state

    fun getPokemonList(limit: Int = 20, offset: Int = 0) {
        addDisposable(
            pokemonRepository.getPokemonList(limit, offset)
                .subscribeOn(Schedulers.io())
                .map { response -> PokemonState.ResultListResponse(response) }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { state.value = PokemonState.ShowLoading }
                .doFinally { state.value = PokemonState.HideLoading }
                .subscribe(
                    { result -> state.value = result },
                    { error -> state.value = PokemonState.Error(error.localizedMessage) }
                )
        )
    }
}
