package com.as1k.pokemonchik.data.di

import android.content.Context
import androidx.room.Room
import com.as1k.pokemonchik.BuildConfig
import com.as1k.pokemonchik.data.database.PokemonchikDatabase
import org.koin.dsl.module

const val DATABASE_POKEMONCHIK = "pokemonchik_database.db"

val dataBaseModule = module {
    single { createDatabase(get()) }
    single { getQuoteDao(database = get()) }
}

fun createDatabase(context: Context): PokemonchikDatabase =
    Room.databaseBuilder(
        context,
        PokemonchikDatabase::class.java,
        DATABASE_POKEMONCHIK
    )
        .apply { if (BuildConfig.DEBUG) fallbackToDestructiveMigration() }
        .build()

fun getQuoteDao(database: PokemonchikDatabase) = database.getRandomQuoteDao()
