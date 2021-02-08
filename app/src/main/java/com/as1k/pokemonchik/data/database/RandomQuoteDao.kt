package com.as1k.pokemonchik.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.as1k.pokemonchik.data.model.RandomQuoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class RandomQuoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertRandomQuote(quote: RandomQuoteEntity)

    @Query("SELECT * FROM quote LIMIT 1")
    abstract fun getQuote(): Flow<RandomQuoteEntity>

    @Query("DELETE FROM quote")
    abstract fun deleteQuote()
}
