package com.as1k.pokemonchik.presentation.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.as1k.pokemonchik.R
import com.as1k.pokemonchik.data.database.PokemonchikDatabase
import com.as1k.pokemonchik.data.mapper.PokemonInfoMapper
import com.as1k.pokemonchik.data.mapper.PokemonResponseMapper
import com.as1k.pokemonchik.data.mapper.RandomQuoteMapper
import com.as1k.pokemonchik.data.network.ApiService
import com.as1k.pokemonchik.data.repository.PokemonRepositoryImpl
import com.as1k.pokemonchik.data.repository.QuoteRepositoryImpl
import com.as1k.pokemonchik.domain.model.PokemonInfo
import com.as1k.pokemonchik.domain.model.PokemonItem
import com.as1k.pokemonchik.domain.model.RandomQuote
import com.as1k.pokemonchik.domain.repository.PokemonRepository
import com.as1k.pokemonchik.domain.repository.QuoteRepository
import com.as1k.pokemonchik.domain.use_case.PokemonDetailsUseCase
import com.as1k.pokemonchik.domain.use_case.RandomQuoteUseCase
import com.as1k.pokemonchik.presentation.PokemonState
import com.as1k.pokemonchik.presentation.QuoteState
import com.as1k.pokemonchik.presentation.utils.IntentConstants.POKEMON_ITEM
import com.as1k.pokemonchik.presentation.utils.bindPokemonTypes
import com.as1k.pokemonchik.presentation.utils.setProgressViewData
import com.as1k.pokemonchik.presentation.utils.setVisibility
import com.as1k.pokemonchik.presentation.utils.toast
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

class PokemonDetailsActivity : TransformationAppCompatActivity() {

    companion object {
        fun start(
            context: Context,
            transformationLayout: TransformationLayout,
            pokemonItem: PokemonItem
        ) {
            val intent = Intent(context, PokemonDetailsActivity::class.java)
            intent.putExtra(POKEMON_ITEM, pokemonItem)
            TransformationCompat.startActivity(transformationLayout, intent)
        }
    }

    private lateinit var pokemonDetailsViewModel: PokemonDetailsViewModel
    private lateinit var randomQuoteViewModel: RandomQuoteViewModel
    private val pokemonItem by lazy { intent.extras?.getParcelable<PokemonItem>(POKEMON_ITEM) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_details)
        bindViews()
        setInitialData()
        initDependencies()
        getPokemonInfo()
        getRandomQuote()
    }

    private fun initDependencies() {
        val pokemonRepository: PokemonRepository = PokemonRepositoryImpl(
            pokemonApi = ApiService.getPokemonApi(),
            pokemonResponseMapper = PokemonResponseMapper(),
            pokemonInfoMapper = PokemonInfoMapper()
        )
        val pokemonDetailsUseCase = PokemonDetailsUseCase(pokemonRepository)
        val detailsFactory = PokemonDetailsViewModelFactory(pokemonDetailsUseCase)
        pokemonDetailsViewModel = ViewModelProvider(this, detailsFactory).get(PokemonDetailsViewModel::class.java)

        val quoteRepository: QuoteRepository = QuoteRepositoryImpl(
            pokemonApi = ApiService.getPokemonApi(),
            randomQuoteDao = PokemonchikDatabase.getDatabase(this).getRandomQuoteDao(),
            randomQuoteMapper = RandomQuoteMapper()
        )
        val quoteUseCase = RandomQuoteUseCase(quoteRepository)
        val quoteFactory = RandomQuoteViewModelFactory(quoteUseCase)
        randomQuoteViewModel = ViewModelProvider(this, quoteFactory).get(RandomQuoteViewModel::class.java)
    }

    private fun getPokemonInfo() {
        val pokemonName = pokemonItem?.name ?: return

        pokemonDetailsViewModel.getPokemonInfo(pokemonName)
        pokemonDetailsViewModel.liveData.observe(this, Observer { result ->
            when (result) {
                is PokemonState.ShowLoading -> { progressBarPokemonDetails.setVisibility(true) }
                is PokemonState.HideLoading -> { progressBarPokemonDetails.setVisibility(false) }
                is PokemonState.ResultItem -> { setData(result.pokemonDetails) }
                is PokemonState.Error -> { }
            }
        })
    }

    private fun getRandomQuote() {
        randomQuoteViewModel.getRandomQuoteLocal()
        randomQuoteViewModel.liveData.observe(this, Observer { result ->
            when(result) {
                is QuoteState.ShowLoading -> progressBarPokemonDetails.setVisibility(true)
                is QuoteState.HideLoading -> { progressBarPokemonDetails.setVisibility(false) }
                is QuoteState.ResultItem -> { setQuoteData(result.randomQuoteEntity) }
                is QuoteState.Error -> { }
            }
        })
    }

    private fun setInitialData() {
        name.text = pokemonItem?.name
        val pokemonImageUrl = pokemonItem?.getImageUrl()
        if (!pokemonImageUrl.isNullOrEmpty()) {
            bindLoadImagePaletteView(image, pokemonImageUrl, header)
        }
    }

    private fun setData(pokemonInfo: PokemonInfo) {
        ribbonRecyclerView.bindPokemonTypes(pokemonInfo.types)
        index.text = pokemonInfo.getIdString()
        weight.text = pokemonInfo.getWeightString()
        height.text = pokemonInfo.getHeightString()
        progressHp.setProgressViewData(pokemonInfo.getHpString(), PokemonInfo.maxHp, pokemonInfo.hp)
        progressAttack.setProgressViewData(pokemonInfo.getAttackString(), PokemonInfo.maxAttack, pokemonInfo.attack)
        progressDefense.setProgressViewData(pokemonInfo.getDefenseString(), PokemonInfo.maxDefense, pokemonInfo.defense)
        pokemonSpeed.setProgressViewData(pokemonInfo.getSpeedString(), PokemonInfo.maxSpeed, pokemonInfo.speed)
        pokemonExperience.setProgressViewData(pokemonInfo.getExpString(), PokemonInfo.maxExp, pokemonInfo.exp)
    }

    private fun setQuoteData(randomQuote: RandomQuote) {
        // quote custom view will be shown
        toast(randomQuote.toString())
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
