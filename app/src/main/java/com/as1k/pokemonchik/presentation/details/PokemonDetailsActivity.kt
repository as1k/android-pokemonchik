package com.as1k.pokemonchik.presentation.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import com.as1k.pokemonchik.R
import com.as1k.pokemonchik.domain.model.PokemonInfo
import com.as1k.pokemonchik.domain.model.PokemonItem
import com.as1k.pokemonchik.data.network.ApiService
import com.as1k.pokemonchik.presentation.utils.IntentConstants.POKEMON_ITEM
import com.as1k.pokemonchik.presentation.utils.bindPokemonTypes
import com.as1k.pokemonchik.presentation.utils.setProgressViewData
import com.as1k.pokemonchik.presentation.utils.setVisibility
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
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class PokemonDetailsActivity : TransformationAppCompatActivity(), CoroutineScope {

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

    private val job = SupervisorJob()
    private val pokemonItem by lazy { intent.extras?.getParcelable<PokemonItem>(POKEMON_ITEM) }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokemon_details)
        bindViews()
        setInitialData()
        getPokemonInfo()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    private fun getPokemonInfo() {
        val pokemonName = pokemonItem?.name ?: return
        val exceptionHandler =
            CoroutineExceptionHandler { _, exception: Throwable ->
                Log.e("asikn_exceptions", exception.toString())
            }
        launch(exceptionHandler) {
            progressBarPokemonDetails.setVisibility(true)
            val pokemon = withContext(Dispatchers.IO) {
                val response = ApiService.getPokemonApi().getPokemonInfo(pokemonName)
                if (response.isSuccessful) response.body()
                else throw Exception("PokemonItem data exception")
            }
            progressBarPokemonDetails.setVisibility(false)
            pokemon?.let { setData(it) }
        }
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

    private fun bindViews() {
        arrow.setOnClickListener {
            this.onBackPressed()
        }
    }

    fun bindLoadImagePaletteView(view: AppCompatImageView, url: String, paletteView: View) {
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
