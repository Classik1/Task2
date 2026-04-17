package com.example.weather

import android.icu.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun getCurrentDate(): String {
    val format = SimpleDateFormat("EEEE, d MMMM", Locale.forLanguageTag("ru"))
    return "Сегодня, " + format.format(Date())
}