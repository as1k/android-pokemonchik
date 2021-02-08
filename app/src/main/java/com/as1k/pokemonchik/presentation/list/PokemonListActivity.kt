package com.as1k.pokemonchik.presentation.list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.as1k.pokemonchik.R
import com.as1k.pokemonchik.presentation.PokemonState
import com.as1k.pokemonchik.presentation.utils.setVisibility
import com.as1k.pokemonchik.presentation.utils.toast
import com.skydoves.transformationlayout.onTransformationStartContainer
import kotlinx.android.synthetic.main.activity_pokemon_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PokemonListActivity : AppCompatActivity() {

    private val pokemonListViewModel: PokemonListViewModel by viewModel()
    private val pokemonListAdapter by lazy {
        PokemonListAdapter(
            itemClickListener = { item ->
//                PokemonDetailsActivity.start(this, item)
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        onTransformationStartContainer()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_list)
        initPokemonListAdapter()
        getPokemonList()

        srlPokemonList.setOnRefreshListener {
            pokemonListViewModel.clear()
        }
    }

    private fun initPokemonListAdapter() {
        rvPokemonList.layoutManager = GridLayoutManager(this, 2)
        rvPokemonList.adapter = pokemonListAdapter
    }

    private fun getPokemonList() {
        pokemonListViewModel.liveData.observe(this, Observer { result ->
            when (result) {
                is PokemonState.ShowLoading -> {
                    progressBar.setVisibility(true)
                }
                is PokemonState.HideLoading -> {
                    progressBar.setVisibility(false)
                    srlPokemonList.isRefreshing = false
                }
                is PokemonState.Error -> { result.error?.let { toast(it) } }
            }
        })

        pokemonListViewModel.pagedListLiveData.observe(this, Observer { result ->
            pokemonListAdapter.submitList(result)
        })
    }
}
