package com.as1k.pokemonchik.presentation.source

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.as1k.pokemonchik.domain.model.PokemonItem
import com.as1k.pokemonchik.domain.use_case.PokemonListUseCase
import com.as1k.pokemonchik.presentation.PokemonState
import com.as1k.pokemonchik.presentation.utils.QueryParams
import com.as1k.pokemonchik.presentation.utils.safeCollect
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import timber.log.Timber

class PokemonDataSource(
    private val pokemonListUseCase: PokemonListUseCase,
    private val viewModelScope: CoroutineScope
) : PageKeyedDataSource<Pair<Int, Int>, PokemonItem>() {

    companion object {
        const val DEFAULT_LIMIT = 20
        private const val DEFAULT_OFFSET = 1
    }

    private val stateMutableLiveData = MutableLiveData<PokemonState>()

    override fun loadInitial(
        params: LoadInitialParams<Pair<Int, Int>>,
        callback: LoadInitialCallback<Pair<Int, Int>, PokemonItem>
    ) {
        viewModelScope.launch {
            stateMutableLiveData.postValue(PokemonState.ShowLoading)
            pokemonListUseCase.getPokemonList(DEFAULT_LIMIT, DEFAULT_OFFSET)
                .catch { throwable ->
                    Timber.e(throwable)
                    stateMutableLiveData.postValue(PokemonState.Error(throwable.message))
                    stateMutableLiveData.postValue(PokemonState.HideLoading)
                }
                .safeCollect { result ->
                    val uriNext = Uri.parse(result.next)
                    val limitNext = uriNext.getQueryParameter(QueryParams.LIMIT)?.toInt() ?: DEFAULT_LIMIT
                    val offsetNext =
                        uriNext.getQueryParameter(QueryParams.OFFSET)?.toInt() ?: DEFAULT_OFFSET
                    callback.onResult(result.results, null, Pair(limitNext, offsetNext))
                    stateMutableLiveData.postValue(PokemonState.HideLoading)
                }
        }
    }

    override fun loadAfter(
        params: LoadParams<Pair<Int, Int>>,
        callback: LoadCallback<Pair<Int, Int>, PokemonItem>
    ) {
        viewModelScope.launch {
            stateMutableLiveData.postValue(PokemonState.ShowLoading)
            pokemonListUseCase.getPokemonList(params.key.first, params.key.second)
                .catch { throwable ->
                    Timber.e(throwable)
                    stateMutableLiveData.postValue(PokemonState.Error(throwable.message))
                    stateMutableLiveData.postValue(PokemonState.HideLoading)
                }
                .safeCollect { result ->
                    val uriNext = Uri.parse(result.next)
                    val limit = uriNext.getQueryParameter(QueryParams.LIMIT)?.toInt() ?: 0
                    val offset = uriNext.getQueryParameter(QueryParams.OFFSET)?.toInt() ?: 0
                    callback.onResult(result.results, Pair(limit, offset))
                    stateMutableLiveData.postValue(PokemonState.HideLoading)
                }
        }
    }

    override fun loadBefore(
        params: LoadParams<Pair<Int, Int>>,
        callback: LoadCallback<Pair<Int, Int>, PokemonItem>
    ) {

    }

    fun getStateMutableLiveData() = stateMutableLiveData
}
