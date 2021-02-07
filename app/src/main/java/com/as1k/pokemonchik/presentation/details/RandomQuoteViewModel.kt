package com.as1k.pokemonchik.presentation.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.as1k.pokemonchik.domain.use_case.RandomQuoteUseCase
import com.as1k.pokemonchik.presentation.QuoteState
import com.as1k.pokemonchik.presentation.utils.safeCollect
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class RandomQuoteViewModel(
    private val randomQuoteUseCase: RandomQuoteUseCase
) : ViewModel() {

    private val state = MutableLiveData<QuoteState>()
    val liveData: LiveData<QuoteState> = state

    fun getRandomQuoteLocal() {
        viewModelScope.launch {
            state.postValue(QuoteState.ShowLoading)
            randomQuoteUseCase.getQuoteLocal()
                .catch { throwable ->
                    Timber.e(throwable)
                    state.postValue(QuoteState.Error(throwable.message))
                    state.postValue(QuoteState.HideLoading)
                }
                .map { item -> QuoteState.ResultItem(item) }
                .safeCollect { result ->
                    state.value = result
                    state.postValue(QuoteState.HideLoading)
                }
        }
    }
}
