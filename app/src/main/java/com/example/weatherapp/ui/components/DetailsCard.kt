package com.example.weatherapp.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.weatherapp.data.model.WeatherData


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