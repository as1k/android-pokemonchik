package com.as1k.pokemonchik.presentation.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.as1k.pokemonchik.R
import com.as1k.pokemonchik.data.mapper.PokemonInfoMapper
import com.as1k.pokemonchik.data.mapper.PokemonResponseMapper
import com.as1k.pokemonchik.data.network.ApiService
import com.as1k.pokemonchik.data.repository.PokemonRepositoryImpl
import com.as1k.pokemonchik.domain.repository.PokemonRepository
import com.as1k.pokemonchik.presentation.PokemonState
import com.as1k.pokemonchik.presentation.PokemonViewModelFactory
import kotlinx.android.synthetic.main.activity_pokemon_list.*
import com.as1k.pokemonchik.presentation.utils.setVisibility
import com.skydoves.transformationlayout.onTransformationStartContainer

class PokemonListActivity : AppCompatActivity() {

    private lateinit var pokemonListViewModel: PokemonListViewModel
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
        initDependencies()
        initPokemonListAdapter()
        getPokemonList()

        srlPokemonList.setOnRefreshListener {
            getPokemonList()
        }
    }

    private fun initPokemonListAdapter() {
        rvPokemonList.layoutManager = GridLayoutManager(this, 2)
        rvPokemonList.adapter = pokemonListAdapter
    }

    private fun initDependencies() {
        val repository: PokemonRepository = PokemonRepositoryImpl(
            pokemonApi = ApiService.getPokemonApi(),
            pokemonResponseMapper = PokemonResponseMapper(),
            pokemonInfoMapper = PokemonInfoMapper()
        )
        val factory = PokemonViewModelFactory(repository)
        pokemonListViewModel =
            ViewModelProvider(this, factory).get(PokemonListViewModel::class.java)
    }

    private fun getPokemonList() {
        pokemonListViewModel.getPokemonList()
        pokemonListViewModel.liveData.observe(this, Observer { result ->
            when (result) {
                is PokemonState.ShowLoading -> {
                    progressBar.setVisibility(true)
                }
                is PokemonState.HideLoading -> {
                    progressBar.setVisibility(false)
                    srlPokemonList.isRefreshing = false
                }
                is PokemonState.ResultListResponse -> {
                    pokemonListAdapter.submitList(result.pokemonListResponse.results)
                }
                is PokemonState.Error -> {
                }
            }
        })
    }
}
