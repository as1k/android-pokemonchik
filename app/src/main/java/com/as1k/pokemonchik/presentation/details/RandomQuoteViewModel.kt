package com.as1k.pokemonchik.presentation.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.as1k.pokemonchik.domain.use_case.RandomQuoteUseCase
import com.as1k.pokemonchik.presentation.QuoteState
import com.as1k.pokemonchik.presentation.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RandomQuoteViewModel(
    private val randomQuoteUseCase: RandomQuoteUseCase
) : BaseViewModel() {

    private val state = MutableLiveData<QuoteState>()
    val liveData: LiveData<QuoteState> = state

    fun getRandomQuoteLocal() {
        addDisposable(
            randomQuoteUseCase.getQuoteLocal()
                .subscribeOn(Schedulers.io())
                .map { item -> QuoteState.ResultItem(item) }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { state.value = QuoteState.ShowLoading }
                .doFinally { state.value = QuoteState.HideLoading }
                .subscribe(
                    { result -> state.value = result },
                    { error -> state.value = QuoteState.Error(error.localizedMessage) }
                )
        )
    }
}
