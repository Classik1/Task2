package com.example.weatherapp

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
        else -> "🌤️"
    }
    Text(icon, fontSize = 64.sp)
}

@Composable
fun DetailsCard(weather: WeatherData) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(0.1f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            DetailItem("🌡️", "Ощущается", "${weather.feelsLike}°")
            DetailItem("💧", "Влажность", "${weather.humidity}%")
            DetailItem("💨", "Ветер", String.format("%.1f м/с", weather.windSpeed))
            DetailItem("📊", "Давление", String.format("%.1f", weather.pressure))
        }
    }
}

@Composable
fun DetailItem(icon: String, label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(icon)
        Text(label, fontSize = 10.sp, color = Color.White.copy(0.6f))
        Text(value, color = Color.White)
    }
}