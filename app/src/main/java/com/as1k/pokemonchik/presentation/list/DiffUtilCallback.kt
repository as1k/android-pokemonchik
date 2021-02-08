package com.as1k.pokemonchik.presentation.list

import androidx.recyclerview.widget.DiffUtil
import com.as1k.pokemonchik.presentation.model.PokemonItemUI

class DiffUtilCallback : DiffUtil.ItemCallback<PokemonItemUI>() {
    override fun areItemsTheSame(oldItem: PokemonItemUI, newItem: PokemonItemUI): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: PokemonItemUI, newItem: PokemonItemUI): Boolean {
        return oldItem == newItem
    }
}
