package com.as1k.pokemonchik.presentation.details

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.Observer
import com.as1k.pokemonchik.R
import com.as1k.pokemonchik.presentation.PokemonState
import com.as1k.pokemonchik.presentation.QuoteState
import com.as1k.pokemonchik.presentation.model.PokemonInfoUI
import com.as1k.pokemonchik.presentation.model.PokemonItemUI
import com.as1k.pokemonchik.presentation.model.RandomQuoteUI
import com.as1k.pokemonchik.presentation.utils.*
import com.as1k.pokemonchik.presentation.utils.IntentConstants.POKEMON_ITEM
import com.bumptech.glide.Glide
import com.github.florent37.glidepalette.BitmapPalette
import com.github.florent37.glidepalette.GlidePalette
import com.skydoves.rainbow.Rainbow
import com.skydoves.rainbow.RainbowOrientation
import com.skydoves.rainbow.color
import com.skydoves.transformationlayout.TransformationAppCompatActivity
import com.skydoves.transformationlayout.TransformationCompat
import com.skydoves.transformationlayout.TransformationLayout
import kotlinx.android.synthetic.main.activity_pokemon_details.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class PokemonDetailsActivity : TransformationAppCompatActivity() {

    companion object {
        fun start(
            context: Context,
            transformationLayout: TransformationLayout,
            pokemonItem: PokemonItemUI
        ) {
            val intent = context.createIntentFor<PokemonDetailsActivity>(POKEMON_ITEM to pokemonItem)
            TransformationCompat.startActivity(transformationLayout, intent)
        }
    }

    private val pokemonDetailsViewModel: PokemonDetailsViewModel by viewModel()
    private val randomQuoteViewModel: RandomQuoteViewModel by viewModel()
    private val pokemonItem by lazy { intent.extras?.getParcelable<PokemonItemUI>(POKEMON_ITEM) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_details)
        bindViews()
        setInitialData()
        getPokemonInfo()
        observePokemonInfo()
        getRandomQuote()
        observeRandomQuote()
    }

    private fun getPokemonInfo() {
        val pokemonName = pokemonItem?.name ?: return

        pokemonDetailsViewModel.getPokemonInfo(pokemonName)
    }

    private fun observePokemonInfo() {
        pokemonDetailsViewModel.liveData.observe(this, Observer { result ->
            when (result) {
                is PokemonState.ShowLoading -> progressBarPokemonDetails.setVisibility(true)
                is PokemonState.HideLoading -> progressBarPokemonDetails.setVisibility(false)
                is PokemonState.ResultItem -> setData(result.pokemonDetails)
                is PokemonState.Error -> result.error?.let { toast(it) }
            }
        })
    }

    private fun getRandomQuote() {
        randomQuoteViewModel.getRandomQuoteLocal()
    }

    private fun observeRandomQuote() {
        randomQuoteViewModel.liveData.observe(this, Observer { result ->
            when (result) {
                is QuoteState.ShowLoading -> progressBarPokemonDetails.setVisibility(true)
                is QuoteState.HideLoading -> progressBarPokemonDetails.setVisibility(false)
                is QuoteState.ResultItem -> setQuoteData(result.randomQuoteEntity)
                is QuoteState.Error -> result.error?.let { toast(it) }
            }
        })
    }

    private fun setInitialData() {
        name.text = pokemonItem?.name
        val pokemonImageUrl = pokemonItem?.url
        if (!pokemonImageUrl.isNullOrEmpty()) {
            bindLoadImagePaletteView(image, pokemonImageUrl, header)
        }
    }

    private fun setData(pokemonInfo: PokemonInfoUI) {
        ribbonRecyclerView.bindPokemonTypes(pokemonInfo.types)
        index.text = pokemonInfo.getIdString()
        weight.text = pokemonInfo.getWeightString()
        height.text = pokemonInfo.getHeightString()
        progressHp.setProgressViewData(pokemonInfo.getHpString(), PokemonInfoUI.maxHp, pokemonInfo.hp)
        progressAttack.setProgressViewData(pokemonInfo.getAttackString(), PokemonInfoUI.maxAttack, pokemonInfo.attack)
        progressDefense.setProgressViewData(pokemonInfo.getDefenseString(), PokemonInfoUI.maxDefense, pokemonInfo.defense)
        pokemonSpeed.setProgressViewData(pokemonInfo.getSpeedString(), PokemonInfoUI.maxSpeed, pokemonInfo.speed)
        pokemonExperience.setProgressViewData(pokemonInfo.getExpString(), PokemonInfoUI.maxExp, pokemonInfo.exp)
    }

    private fun setQuoteData(randomQuote: RandomQuoteUI) {
        quoteView.setQuoteAndAuthor(randomQuote.quoteText, randomQuote.quoteAuthor)
    }

    private fun bindViews() {
        arrow.setOnClickListener {
            this.onBackPressed()
        }
    }

    private fun bindLoadImagePaletteView(view: AppCompatImageView, url: String, paletteView: View) {
        val context = view.context
        Glide.with(context)
            .load(url)
            .listener(
                GlidePalette.with(url)
                    .use(BitmapPalette.Profile.MUTED_LIGHT)
                    .intoCallBack { palette ->
                        val light = palette?.lightVibrantSwatch?.rgb
                        val domain = palette?.dominantSwatch?.rgb
                        if (domain != null) {
                            if (light != null) {
                                Rainbow(paletteView).palette {
                                    +color(domain)
                                    +color(light)
                                }.background(orientation = RainbowOrientation.TOP_BOTTOM)
                            } else {
                                paletteView.setBackgroundColor(domain)
                            }
                            if (context is AppCompatActivity) {
                                context.window.apply {
                                    addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                                    statusBarColor = domain
                                }
                            }
                        }
                    }.crossfade(true)
            ).into(view)
    }
}
