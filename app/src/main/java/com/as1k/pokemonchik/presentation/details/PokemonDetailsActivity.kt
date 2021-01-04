package com.as1k.pokemonchik.presentation.details

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import com.as1k.pokemonchik.R
import com.as1k.pokemonchik.model.PokemonInfo
import com.as1k.pokemonchik.model.PokemonItem
import com.as1k.pokemonchik.network.ApiService
import com.as1k.pokemonchik.presentation.utils.IntentConstants.POKEMON_ITEM
import com.as1k.pokemonchik.presentation.utils.PokemonTypeUtils
import com.as1k.pokemonchik.presentation.utils.SpacesItemDecoration
import com.as1k.pokemonchik.presentation.utils.setVisibility
import com.as1k.pokemonchik.presentation.utils.whatIfNotNullOrEmpty
import com.bumptech.glide.Glide
import com.github.florent37.glidepalette.BitmapPalette
import com.github.florent37.glidepalette.GlidePalette
import com.skydoves.androidribbon.RibbonRecyclerView
import com.skydoves.androidribbon.ribbonView
import com.skydoves.rainbow.Rainbow
import com.skydoves.rainbow.RainbowOrientation
import com.skydoves.rainbow.color
import kotlinx.android.synthetic.main.activity_pokemon_details.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class PokemonDetailsActivity : AppCompatActivity(), CoroutineScope {

    companion object {
        fun start(context: Context, pokemonItem: PokemonItem) {
            val intent = Intent(context, PokemonDetailsActivity::class.java)
            intent.putExtra(POKEMON_ITEM, pokemonItem)
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

    private fun setData(pokemonInfo: PokemonInfo) {
        index.text = pokemonInfo.getIdString()
        name.text = pokemonInfo.name
        val pokemonImageUrl = pokemonItem?.getImageUrl()
        if (!pokemonImageUrl.isNullOrEmpty()) {
            bindLoadImagePaletteView(image, pokemonImageUrl, header)
        }
        bindPokemonTypes(ribbonRecyclerView, pokemonInfo.types)
        weight.text = pokemonInfo.getWeightString()
        height.text = pokemonInfo.getHeightString()
        progressHp.labelText = pokemonInfo.getHpString()
        progressHp.max = PokemonInfo.maxHp.toFloat()
        progressHp.progress = pokemonInfo.hp.toFloat()
        progressAttack.labelText = pokemonInfo.getAttackString()
        progressAttack.max = PokemonInfo.maxAttack.toFloat()
        progressAttack.progress = pokemonInfo.attack.toFloat()
        progressDefense.labelText = pokemonInfo.getDefenseString()
        progressDefense.max = PokemonInfo.maxDefense.toFloat()
        progressDefense.progress = pokemonInfo.defense.toFloat()
        pokemonSpeed.labelText = pokemonInfo.getSpeedString()
        pokemonSpeed.max = PokemonInfo.maxSpeed.toFloat()
        pokemonSpeed.progress = pokemonInfo.speed.toFloat()
        pokemonExperience.labelText = pokemonInfo.getExpString()
        pokemonExperience.max = PokemonInfo.maxExp.toFloat()
        pokemonExperience.progress = pokemonInfo.exp.toFloat()
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

    fun bindPokemonTypes(recyclerView: RibbonRecyclerView, types: List<PokemonInfo.TypeResponse>?) {
        types.whatIfNotNullOrEmpty {
            recyclerView.clear()
            for (type in it) {
                with(recyclerView) {
                    addRibbon(
                        ribbonView(context) {
                            setText(type.type.name)
                            setTextColor(Color.WHITE)
                            setPaddingLeft(84f)
                            setPaddingRight(84f)
                            setPaddingTop(2f)
                            setPaddingBottom(10f)
                            setTextSize(16f)
                            setRibbonRadius(120f)
                            setTextStyle(Typeface.BOLD)
                            setRibbonBackgroundColorResource(
                                PokemonTypeUtils.getTypeColor(type.type.name)
                            )
                        }.apply {
                            maxLines = 1
                            gravity = Gravity.CENTER
                        }
                    )
                    addItemDecoration(SpacesItemDecoration())
                }
            }
        }
    }
}
