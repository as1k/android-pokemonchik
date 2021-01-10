package com.as1k.pokemonchik.presentation.source

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.as1k.pokemonchik.domain.model.PokemonItem
import com.as1k.pokemonchik.domain.use_case.PokemonListUseCase
import com.as1k.pokemonchik.presentation.PokemonState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class PokemonDataSource(
    private val pokemonListUseCase: PokemonListUseCase,
    private val compositeDisposable: CompositeDisposable
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
        compositeDisposable.add(
            pokemonListUseCase.getPokemonList(DEFAULT_LIMIT, DEFAULT_OFFSET)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { stateMutableLiveData.postValue(PokemonState.ShowLoading) }
                .doFinally { stateMutableLiveData.postValue(PokemonState.HideLoading) }
                .subscribe(
                    { result ->
                        val uriNext = Uri.parse(result.next)
                        val limitNext = uriNext.getQueryParameter("limit")?.toInt() ?: DEFAULT_LIMIT
                        val offsetNext =
                            uriNext.getQueryParameter("offset")?.toInt() ?: DEFAULT_OFFSET
                        callback.onResult(result.results, null, Pair(limitNext, offsetNext))
                    },
                    { error -> stateMutableLiveData.postValue(PokemonState.Error(error.localizedMessage)) }
                )
        )
    }

    override fun loadAfter(
        params: LoadParams<Pair<Int, Int>>,
        callback: LoadCallback<Pair<Int, Int>, PokemonItem>
    ) {
        compositeDisposable.add(
            pokemonListUseCase.getPokemonList(params.key.first, params.key.second)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { stateMutableLiveData.postValue(PokemonState.ShowLoading) }
                .doFinally { stateMutableLiveData.postValue(PokemonState.HideLoading) }
                .subscribe(
                    { result ->
                        val uriNext = Uri.parse(result.next)
                        val limit = uriNext.getQueryParameter("limit")?.toInt() ?: 0
                        val offset = uriNext.getQueryParameter("offset")?.toInt() ?: 0
                        callback.onResult(result.results, Pair(limit, offset))
                    },
                    { error -> stateMutableLiveData.postValue(PokemonState.Error(error.localizedMessage)) }
                )
        )
    }

    override fun loadBefore(
        params: LoadParams<Pair<Int, Int>>,
        callback: LoadCallback<Pair<Int, Int>, PokemonItem>
    ) {

    }

    fun getStateMutableLiveData() = stateMutableLiveData
}
