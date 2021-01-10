package com.as1k.pokemonchik.presentation.list

import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import com.as1k.pokemonchik.R
import com.as1k.pokemonchik.domain.model.PokemonItem
import com.as1k.pokemonchik.presentation.base.BaseViewHolder
import com.as1k.pokemonchik.presentation.details.PokemonDetailsActivity
import com.bumptech.glide.Glide
import com.github.florent37.glidepalette.BitmapPalette
import com.github.florent37.glidepalette.GlidePalette
import com.google.android.material.card.MaterialCardView
import com.skydoves.transformationlayout.TransformationLayout

class PokemonListAdapter(
    private val itemClickListener: ((item: PokemonItem) -> Unit)? = null
) : ListAdapter<PokemonItem, PokemonListAdapter.PokemonViewHolder>(DiffUtilCallback()) {

    private var previousTime = SystemClock.elapsedRealtime()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PokemonViewHolder(
            view = inflater.inflate(R.layout.item_pokemon, parent, false),
            itemClickListener = itemClickListener
        )
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class PokemonViewHolder(
        private val view: View,
        private val itemClickListener: ((item: PokemonItem) -> Unit)? = null
    ) : BaseViewHolder(view) {

        val transformationLayout: TransformationLayout
        private val cardView: MaterialCardView
        private val pokemonImage: AppCompatImageView
        private val pokemonName: TextView

        init {
            transformationLayout = view.findViewById(R.id.transformationLayout)
            cardView = view.findViewById(R.id.cardView)
            pokemonImage = view.findViewById(R.id.pokemonImage)
            pokemonName = view.findViewById(R.id.pokemonName)
        }

        fun bind(item: PokemonItem) {
            bindLoadImagePalette(pokemonImage, item.getImageUrl(), cardView)
            pokemonName.text = item.name

            view.setOnClickListener {
//                itemClickListener?.invoke(item)
                val now = SystemClock.elapsedRealtime()
                if (now - previousTime >= transformationLayout.duration) {
                    PokemonDetailsActivity.start(view.context, transformationLayout, item)
                    previousTime = now
                }
            }
        }

        override fun clear() {}
    }

    fun bindLoadImagePalette(view: AppCompatImageView, url: String, paletteCard: MaterialCardView) {
        Glide.with(view.context)
            .load(url)
            .listener(
                GlidePalette.with(url)
                    .use(BitmapPalette.Profile.MUTED_LIGHT)
                    .intoCallBack { palette ->
                        val rgb = palette?.dominantSwatch?.rgb
                        if (rgb != null) {
                            paletteCard.setCardBackgroundColor(rgb)
                        }
                    }.crossfade(true)
            ).into(view)
    }
}
