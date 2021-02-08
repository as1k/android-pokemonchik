package com.as1k.pokemonchik.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.as1k.pokemonchik.data.database.RoomConstants.DATABASE_POKEMONCHIK_VERSION
import com.as1k.pokemonchik.data.model.RandomQuoteEntity

@Database(entities = [RandomQuoteEntity::class], version = DATABASE_POKEMONCHIK_VERSION, exportSchema = false)
abstract class PokemonchikDatabase : RoomDatabase() {
    abstract fun getRandomQuoteDao(): RandomQuoteDao
}
