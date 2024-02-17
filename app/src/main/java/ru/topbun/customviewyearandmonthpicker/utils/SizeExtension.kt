package ru.topbun.customviewyearandmonthpicker.utils

import android.content.Context


fun Number.pxToSp(context: Context): Float {
    val scaledDensity = context.resources.displayMetrics.scaledDensity
    return this.toFloat() * scaledDensity
}

fun Number.spToPp(context: Context): Float {
    val scaledDensity = context.resources.displayMetrics.scaledDensity
    return this.toFloat() / scaledDensity
}