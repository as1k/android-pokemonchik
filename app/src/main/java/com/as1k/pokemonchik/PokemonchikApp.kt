package com.as1k.pokemonchik

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.work.*
import androidx.work.PeriodicWorkRequest.MIN_PERIODIC_FLEX_MILLIS
import androidx.work.PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS
import com.as1k.pokemonchik.worker.RandomQuoteWorker
import timber.log.Timber
import java.util.concurrent.TimeUnit


const val RANDOM_QUOTE_PERIODIC_WORK = "random_quote_periodic_work"
const val RANDOM_QUOTE_ONE_TIME_WORK = "random_quote_one_time_work"

class PokemonchikApp : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        ProcessLifecycleOwner.get().lifecycle.addObserver(PokemonchikLifecycleOwner(this))
        startFetchRandomQuote()
    }

    private fun startFetchRandomQuote() {
        val workManager = WorkManager.getInstance(this)
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .build()

        val initWorkRequest = OneTimeWorkRequestBuilder<RandomQuoteWorker>()
            .addTag(RANDOM_QUOTE_ONE_TIME_WORK)
            .setConstraints(constraints)
            .build()

        val periodicWorkRequest = PeriodicWorkRequestBuilder<RandomQuoteWorker>(
            MIN_PERIODIC_INTERVAL_MILLIS, TimeUnit.MILLISECONDS, MIN_PERIODIC_FLEX_MILLIS, TimeUnit.MILLISECONDS
        )
            .addTag(RANDOM_QUOTE_PERIODIC_WORK)
            .setConstraints(constraints)
            .build()

        workManager.beginUniqueWork(
            RANDOM_QUOTE_ONE_TIME_WORK, ExistingWorkPolicy.KEEP, initWorkRequest
        ).enqueue()

        workManager.enqueueUniquePeriodicWork(
            RANDOM_QUOTE_PERIODIC_WORK, ExistingPeriodicWorkPolicy.REPLACE, periodicWorkRequest
        )
    }
}
