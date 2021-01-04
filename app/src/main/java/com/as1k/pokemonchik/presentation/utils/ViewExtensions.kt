package com.as1k.pokemonchik.presentation.utils

import android.view.View

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
