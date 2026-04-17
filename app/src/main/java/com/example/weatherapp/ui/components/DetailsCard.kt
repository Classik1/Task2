package com.example.weatherapp.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.R
import com.example.repository.WeatherData
@SuppressLint("DefaultLocale")
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

            Text(
                text = weather.city,
                fontSize = 20.sp,
                color = Color.White.copy(0.8f),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(
                text = "${weather.temperature}°",
                fontSize = 48.sp,
                fontWeight = FontWeight.Light,
                color = Color.White,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
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

            Row(modifier = Modifier.fillMaxWidth()) {
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