package com.as1k.pokemonchik.presentation.utils

import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import com.as1k.pokemonchik.presentation.model.TypeItemUI
import com.skydoves.androidribbon.RibbonRecyclerView
import com.skydoves.androidribbon.ribbonView
import com.skydoves.progressview.ProgressView

fun View?.setVisibility(progress: Boolean) {
    if (progress) {
        this?.post {
            animate()
                .withStartAction { this.visible() }
                .alpha(1f)
                .setDuration(100)
                .start()
        }
    } else {
        this?.post {
            animate()
                .alpha(0f)
                .setDuration(100)
                .withEndAction { this.visible() }
                .start()
        }
    }
}

fun View.visible() {
    post {
        visibility = View.VISIBLE
    }
}

fun View.invisible() {
    post {
        visibility = View.INVISIBLE
    }
}

fun View.gone() {
    post {
        visibility = View.GONE
    }
}

fun View?.isVisible(): Boolean {
    return if (this == null) {
        false
    } else {
        this.visibility == View.VISIBLE
    }
}

fun ProgressView.setProgressViewData(labelText: String, max: Int, progress: Int) {
    this.labelText = labelText
    this.max = max.toFloat()
    this.progress = progress.toFloat()
}

fun RibbonRecyclerView.bindPokemonTypes(types: List<TypeItemUI>?) {
    this.clear()
    if (types != null) {
        for (type in types) {
            with(this) {
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
