package com.as1k.pokemonchik.presentation.list

import androidx.recyclerview.widget.DiffUtil
import com.as1k.pokemonchik.domain.model.PokemonItem

class DiffUtilCallback : DiffUtil.ItemCallback<PokemonItem>() {
    override fun areItemsTheSame(oldItem: PokemonItem, newItem: PokemonItem): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: PokemonItem, newItem: PokemonItem): Boolean {
        return oldItem == newItem
    }
}
