package com.as1k.pokemonchik.presentation.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.as1k.pokemonchik.domain.use_case.PokemonDetailsUseCase
import com.as1k.pokemonchik.presentation.PokemonState
import com.as1k.pokemonchik.presentation.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PokemonDetailsViewModel(
    private val pokemonDetailsUseCase: PokemonDetailsUseCase
) : BaseViewModel() {

    private val state = MutableLiveData<PokemonState>()
    val liveData: LiveData<PokemonState> = state

    fun getPokemonInfo(pokemonName: String) {
        addDisposable(
            pokemonDetailsUseCase.getPokemonInfo(pokemonName)
                .subscribeOn(Schedulers.io())
                .map { item -> PokemonState.ResultItem(item) }
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
