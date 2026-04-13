package com.example.weatherapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.weatherapp.R
import com.example.weatherapp.ui.components.WeatherIcon
import com.example.weatherapp.viewmodel.WeatherViewModel

@Composable
fun DetailsScreen(
    vm: WeatherViewModel,
    navController: NavController
) {

    val weather by vm.weather.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {

        weather?.let { w ->
            Image(
                painter = painterResource(getBackground(w.condition)),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.35f))
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .padding(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                    Text(
                        text = "Подробная погода",
                        color = Color.White,
                        fontSize = 20.sp
                    )
                }

                Spacer(Modifier.height(16.dp))
                Text(
                    w.city,
                    fontSize = 28.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(8.dp))

                WeatherIcon(w.condition)

                Text(
                    "${w.temperature}°",
                    fontSize = 72.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Light
                )

                Text(
                    w.condition,
                    color = Color.White.copy(0.8f)
                )

                Spacer(Modifier.height(16.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White.copy(0.1f)
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {

                        DetailRow("Ощущается", "${w.feelsLike}°")
                        DetailRow("Влажность", "${w.humidity}%")
                        DetailRow("Ветер", "%.1f м/с".format(w.windSpeed))
                        DetailRow("Давление", "${w.pressure} hPa")
                    }
                }

                Spacer(Modifier.height(16.dp))

                Text(
                    "Прогноз на неделю",
                    color = Color.White,
                    fontSize = 18.sp
                )

                Spacer(Modifier.height(8.dp))

                LazyColumn {
                    items(w.forecast) { day ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(day.date, color = Color.White)
                            WeatherIcon(day.condition)
                            Text(
                                "${day.maxTemp}° / ${day.minTemp}°",
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = Color.White.copy(0.7f))
        Text(value, color = Color.White)
    }
}