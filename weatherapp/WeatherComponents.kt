package com.example.weatherapp

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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

@Composable
fun DetailsCard(weather: WeatherData) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(0.08f)
        ),
        elevation = CardDefaults.cardElevation(0.dp),
        border = BorderStroke(1.dp, Color.White.copy(0.2f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                DetailItem(
                    icon = "🌡️",
                    label = "Ощущается",
                    value = "${weather.feelsLike}°",
                    modifier = Modifier.weight(1f)
                )
                DetailItem(
                    icon = "💧",
                    label = "Влажность",
                    value = "${weather.humidity}%",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                DetailItem(
                    icon = "💨",
                    label = "Ветер",
                    value = String.format("%.1f м/с", weather.windSpeed),
                    modifier = Modifier.weight(1f)
                )
                DetailItem(
                    icon = "📊",
                    label = "Давление",
                    value = "${weather.pressure.toInt()} hPa",
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun DetailItem(
    icon: String,
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(icon)
        Text(label, fontSize = 10.sp, color = Color.White.copy(0.6f))
        Text(value, color = Color.White)
    }
}