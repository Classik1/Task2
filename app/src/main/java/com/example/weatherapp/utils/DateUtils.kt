package com.example.weatherapp.utils

import android.icu.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun getCurrentDate(): String {
    val format = SimpleDateFormat("EEEE, d MMMM", Locale("ru"))
    return "Сегодня, " + format.format(Date())
}