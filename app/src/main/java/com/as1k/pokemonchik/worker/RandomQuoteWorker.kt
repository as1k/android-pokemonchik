package com.as1k.pokemonchik.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.as1k.pokemonchik.data.database.PokemonchikDatabase
import com.as1k.pokemonchik.data.mapper.RandomQuoteMapper
import com.as1k.pokemonchik.data.network.ApiService
import com.as1k.pokemonchik.data.repository.QuoteRepositoryImpl
import com.as1k.pokemonchik.domain.repository.QuoteRepository
import com.as1k.pokemonchik.domain.use_case.RandomQuoteUseCase
import com.as1k.pokemonchik.presentation.utils.safeCollect
import kotlinx.coroutines.flow.catch
import timber.log.Timber

class RandomQuoteWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    val repository: QuoteRepository = QuoteRepositoryImpl(
        pokemonApi = ApiService.getPokemonApi(),
        randomQuoteDao = PokemonchikDatabase.getDatabase(context).getRandomQuoteDao(),
        randomQuoteMapper = RandomQuoteMapper()
    )
    val randomQuoteUseCase = RandomQuoteUseCase(repository)

    override suspend fun doWork(): Result {
        return try {
            randomQuoteUseCase.getRandomQuote()
                .catch { throwable ->
                    Timber.e(throwable)
                    Result.failure()
                }
                .safeCollect { result ->
                    Timber.d(result.toString())
                    randomQuoteUseCase.deleteQuote()
                    randomQuoteUseCase.insertRandomQuote(result)
                    Timber.d(result.toString())
                }
            Result.success()
        } catch (error: Throwable) {
            Result.failure()
        }
    }
}
