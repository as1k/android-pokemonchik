package com.as1k.pokemonchik.presentation.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.as1k.pokemonchik.R
import com.as1k.pokemonchik.network.ApiService
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import com.as1k.pokemonchik.presentation.utils.setProgress
import java.io.IOException

class MainActivity : AppCompatActivity(), CoroutineScope {

    private val job = SupervisorJob()
    private val pokemonListAdapter by lazy { PokemonListAdapter() }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initPokemonListAdapter()
        getPokemonList()

        srlPokemonList.setOnRefreshListener {
            pokemonListAdapter.clearAll()
            getPokemonList()
        }
    }

    private fun initPokemonListAdapter() {
        rvPokemonList
        rvPokemonList.layoutManager = GridLayoutManager(this, 2)
        rvPokemonList.adapter = pokemonListAdapter
    }

    private fun getPokemonList() {
        val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
            Log.e("asikn_exceptions", exception.toString())
        }
        launch(coroutineExceptionHandler) {
            progressBar.setProgress(true)
            val list = withContext(Dispatchers.IO) {
                val response = ApiService.getPokemonApi()
                    .getPokemonList()
                if (response.isSuccessful) {
                    response.body()?.results ?: emptyList()
                } else {
                    throw IOException("")
                }
            }
            Log.d("movie_data_list", list.toString())
            progressBar.setProgress(false)
            pokemonListAdapter.addItems(list)
        }
    }
}
