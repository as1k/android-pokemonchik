package com.as1k.pokemonchik.presentation.list

import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.as1k.pokemonchik.R
import com.as1k.pokemonchik.data.model.PokemonItem
import com.as1k.pokemonchik.presentation.base.BaseViewHolder
import com.as1k.pokemonchik.presentation.details.PokemonDetailsActivity
import com.bumptech.glide.Glide
import com.github.florent37.glidepalette.BitmapPalette
import com.github.florent37.glidepalette.GlidePalette
import com.google.android.material.card.MaterialCardView
import com.skydoves.transformationlayout.TransformationLayout

class PokemonListAdapter(
    private val itemClickListener: ((item: PokemonItem) -> Unit)? = null
) : RecyclerView.Adapter<BaseViewHolder>() {

    private val VIEW_TYPE_LOADING = 0
    private val VIEW_TYPE_NORMAL = 1

    private var isLoaderVisible = false

    private val pokemons = ArrayList<PokemonItem>()
    private var previousTime = SystemClock.elapsedRealtime()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_NORMAL -> PokemonViewHolder(
                view = inflater.inflate(R.layout.item_pokemon, parent, false),
                itemClickListener = itemClickListener
            )
            VIEW_TYPE_LOADING -> ProgressViewHolder(
                view = inflater.inflate(R.layout.item_loading, parent, false)
            )
            else -> throw Throwable("Invalid view")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (isLoaderVisible) {
            if (position == pokemons.size - 1) {
                VIEW_TYPE_LOADING
            } else {
                VIEW_TYPE_NORMAL
            }
        } else {
            VIEW_TYPE_NORMAL
        }
    }

    override fun getItemCount(): Int = pokemons.size

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        if (holder is PokemonViewHolder) {
            holder.bind(pokemons[position])
        }
    }

    fun addItems(list: List<PokemonItem>) {
        pokemons.addAll(list)
        notifyDataSetChanged()
    }

    fun setNewItems(list: List<PokemonItem>) {
        pokemons.clear()
        addItems(list)
        isLoaderVisible = false
    }

    fun getItem(position: Int): PokemonItem? {
        return pokemons.get(position)
    }

    fun clearAll() {
        pokemons.clear()
        notifyDataSetChanged()
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

    inner class ProgressViewHolder(view: View) : BaseViewHolder(view) {

        override fun clear() {}
    }
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
