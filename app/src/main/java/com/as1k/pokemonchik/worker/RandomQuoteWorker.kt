package com.as1k.pokemonchik.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.as1k.pokemonchik.domain.use_case.RandomQuoteUseCase
import com.as1k.pokemonchik.presentation.utils.safeCollect
import kotlinx.coroutines.flow.catch
import timber.log.Timber
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RandomQuoteWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params), KoinComponent {

    val randomQuoteUseCase: RandomQuoteUseCase by inject()

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
