package com.example.levelupgamer.util

import java.text.NumberFormat
import java.util.Locale

fun Double.toClp(): String {
    val nf = NumberFormat.getInstance(Locale("es", "CL"))
    nf.maximumFractionDigits = 0
    nf.minimumFractionDigits = 0
    return nf.format(this)
}

fun Int.toClp(): String {
    val nf = NumberFormat.getInstance(Locale("es", "CL"))
    nf.maximumFractionDigits = 0
    nf.minimumFractionDigits = 0
    return nf.format(this)
}