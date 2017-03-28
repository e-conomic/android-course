package com.e_conomic.weatherapp.extensions

import android.content.Context
import android.support.v4.content.ContextCompat
import java.text.DateFormat
import java.util.*

fun Long.toDateString(dateFormat: Int = DateFormat.MEDIUM): String {
    val df = DateFormat.getDateInstance(dateFormat, Locale.getDefault())
    return df.format(this * 1000)
}

fun Context.color(res: Int): Int = ContextCompat.getColor(this, res)