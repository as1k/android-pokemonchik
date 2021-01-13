package com.as1k.pokemonchik.worker

import android.content.Context
import androidx.work.RxWorker
import androidx.work.WorkerParameters
import com.as1k.pokemonchik.data.database.PokemonchikDatabase
import com.as1k.pokemonchik.data.mapper.RandomQuoteMapper
import com.as1k.pokemonchik.data.network.ApiService
import com.as1k.pokemonchik.data.repository.QuoteRepositoryImpl
import com.as1k.pokemonchik.domain.repository.QuoteRepository
import com.as1k.pokemonchik.domain.use_case.RandomQuoteUseCase
import io.reactivex.Single
import timber.log.Timber

class RandomQuoteWorker(
    context: Context,
    params: WorkerParameters
) : RxWorker(context, params) {

    val repository: QuoteRepository = QuoteRepositoryImpl(
        pokemonApi = ApiService.getPokemonApi(),
        randomQuoteDao = PokemonchikDatabase.getDatabase(context).getRandomQuoteDao(),
        randomQuoteMapper = RandomQuoteMapper()
    )
    val randomQuoteUseCase = RandomQuoteUseCase(repository)

    override fun createWork(): Single<Result> {
        return randomQuoteUseCase.getRandomQuote()
            .doOnSuccess { result ->
                randomQuoteUseCase.deleteQuote()
                randomQuoteUseCase.insertRandomQuote(result)
                Timber.d(result.toString())
            }
            .map { Result.success() }
            .onErrorReturn { Result.failure() }
    }
}
