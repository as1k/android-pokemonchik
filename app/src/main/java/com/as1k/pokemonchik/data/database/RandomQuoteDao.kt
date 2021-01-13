package com.as1k.pokemonchik.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.as1k.pokemonchik.data.model.RandomQuoteEntity
import io.reactivex.Flowable

@Dao
abstract class RandomQuoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertRandomQuote(quote: RandomQuoteEntity)

    @Query("SELECT * FROM quote LIMIT 1")
    abstract fun getQuote(): Flowable<RandomQuoteEntity>

    @Query("DELETE FROM quote")
    abstract fun deleteQuote()
}
