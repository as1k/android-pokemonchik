package com.as1k.pokemonchik

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.work.WorkManager

class PokemonchikLifecycleOwner(private val context: Context) : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        WorkManager.getInstance(context).cancelUniqueWork(RANDOM_QUOTE_PERIODIC_WORK)
    }
}
