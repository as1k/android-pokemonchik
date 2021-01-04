package com.as1k.pokemonchik.presentation.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.as1k.pokemonchik.R
import com.as1k.pokemonchik.network.ApiService
import com.as1k.pokemonchik.presentation.details.PokemonDetailsActivity
import kotlinx.android.synthetic.main.activity_pokemon_list.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import com.as1k.pokemonchik.presentation.utils.setVisibility
import java.io.IOException

class PokemonListActivity : AppCompatActivity(), CoroutineScope {

    private val job = SupervisorJob()
    private val pokemonListAdapter by lazy {
        PokemonListAdapter(
            itemClickListener = { item ->
                PokemonDetailsActivity.start(this, item)
            }
        )
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_list)

        initPokemonListAdapter()
        getPokemonList()

        srlPokemonList.setOnRefreshListener {
            pokemonListAdapter.clearAll()
            getPokemonList()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    private fun initPokemonListAdapter() {
        rvPokemonList.layoutManager = GridLayoutManager(this, 2)
        rvPokemonList.adapter = pokemonListAdapter
    }

    private fun getPokemonList() {
        val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
            Log.e("asikn_exceptions", exception.toString())
        }
        launch(coroutineExceptionHandler) {
            progressBar.setVisibility(true)
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
            srlPokemonList.isRefreshing = false
            progressBar.setVisibility(false)
            pokemonListAdapter.addItems(list)
        }
    }
}
