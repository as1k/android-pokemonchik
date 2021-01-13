package com.as1k.pokemonchik.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.as1k.pokemonchik.BuildConfig
import com.as1k.pokemonchik.data.database.RoomConstants.DATABASE_POKEMONCHIK
import com.as1k.pokemonchik.data.database.RoomConstants.DATABASE_POKEMONCHIK_VERSION
import com.as1k.pokemonchik.data.model.RandomQuoteEntity

@Database(entities = [RandomQuoteEntity::class], version = DATABASE_POKEMONCHIK_VERSION, exportSchema = false)
abstract class PokemonchikDatabase : RoomDatabase() {

    companion object {

        fun getDatabase(context: Context): PokemonchikDatabase {
            return Room.databaseBuilder(context, PokemonchikDatabase::class.java, DATABASE_POKEMONCHIK)
                .apply { if (BuildConfig.DEBUG) fallbackToDestructiveMigration() }
                .build()
        }
    }

    abstract fun getRandomQuoteDao(): RandomQuoteDao
}
