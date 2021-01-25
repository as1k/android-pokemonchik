package com.as1k.pokemonchik.presentation.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.as1k.pokemonchik.domain.use_case.PokemonDetailsUseCase
import com.as1k.pokemonchik.presentation.PokemonState
import com.as1k.pokemonchik.presentation.utils.safeCollect
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber

class PokemonDetailsViewModel(
    private val pokemonDetailsUseCase: PokemonDetailsUseCase
) : ViewModel() {

    private val state = MutableLiveData<PokemonState>()
    val liveData: LiveData<PokemonState> = state

    fun getPokemonInfo(pokemonName: String) {
        viewModelScope.launch {
            state.postValue(PokemonState.ShowLoading)
            pokemonDetailsUseCase.getPokemonInfo(pokemonName)
                .catch { throwable ->
                    Timber.e(throwable)
                    state.postValue(PokemonState.Error(throwable.message))
                    state.postValue(PokemonState.HideLoading)
                }
                .map { item -> PokemonState.ResultItem(item) }
                .safeCollect { result ->
                    state.postValue(result)
                    state.postValue(PokemonState.HideLoading)
                }
        }
    }
}
