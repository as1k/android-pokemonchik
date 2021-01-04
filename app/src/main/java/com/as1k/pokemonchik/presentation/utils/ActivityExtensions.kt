package com.as1k.pokemonchik.presentation.utils

import androidx.activity.ComponentActivity
import com.skydoves.transformationlayout.onTransformationEndContainer

fun ComponentActivity.onTransformationEndContainerApplyParams() {
    onTransformationEndContainer(intent.getParcelableExtra("transformationLayout"))
}
