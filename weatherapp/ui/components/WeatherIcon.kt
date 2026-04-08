package com.example.weatherapp.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun WeatherIcon(condition: String) {
    val icon = when {
        condition.contains("rain", true) -> "🌧️"
        condition.contains("cloud", true) -> "☁️"
        condition.contains("clear", true) -> "☀️"
        condition.contains("snow", true) -> "❄️"
        condition.contains("thunder", true) -> "⛈️"
        condition.contains("mist", true) -> "🌫️"
        else -> "🌤️"
    }

    Text(
        icon,
        fontSize = 72.sp,
        modifier = Modifier.animateContentSize()
    )
}