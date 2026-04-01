package com.example.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherapp.api.RetrofitInstance
import com.example.weatherapp.data.DatabaseProvider
import com.example.weatherapp.data.WeatherRepository
import com.example.weatherapp.viewmodel.WeatherViewModel
import com.example.weatherapp.viewmodel.WeatherViewModelFactory
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val apiKey = "d050c24954604d2889a140805260104"

        val repository = WeatherRepository(
            api = RetrofitInstance.api,
            dao = DatabaseProvider.getDatabase(this).weatherDao(),
            apiKey = apiKey
        )

        setContent {
            val weatherViewModel: WeatherViewModel = viewModel(
                factory = WeatherViewModelFactory(repository)
            )

            MaterialTheme(colorScheme = darkColorScheme()) {
                WeatherApp(weatherViewModel)
            }
        }
    }
}
private fun darkColorScheme() = darkColorScheme(
    primary = Color(0xFF4A90E2),
    secondary = Color(0xFF2C3E50),
    background = Color(0xFF1E3C72),
    surface = Color(0xFF2C3E50),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White
)

@Composable
fun WeatherApp(viewModel: WeatherViewModel) {
    var city by remember { mutableStateOf("Москва") }
    val weather by viewModel.weather.collectAsState()
    val loading by viewModel.loading.collectAsState()

    //(костыль)
//    val forecast = listOf(
//        DailyForecast("ПН", "☀️", 25, 18),
//        DailyForecast("ВТ", "⛅", 23, 17),
//        DailyForecast("СР", "🌧️", 20, 15),
//        DailyForecast("ЧТ", "☁️", 21, 16),
//        DailyForecast("ПТ", "☀️", 24, 18),
//        DailyForecast("СБ", "☀️", 26, 19),
//        DailyForecast("ВС", "🌧️", 22, 16)
//    )

    LaunchedEffect(city) { viewModel.loadWeather(city) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1E3C72))
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        TopBar(city = city)

        if (loading) {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                "Загрузка...",
                color = Color.White,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        } else {
            weather?.let { w ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(24.dp))

                    WeatherIcon(w.condition)

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "${w.temperature}°",
                        fontSize = 64.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Text(
                        text = w.condition,
                        fontSize = 20.sp,
                        color = Color.White
                    )

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("↑ ${w.maxTemp}°", fontSize = 14.sp, color = Color.White)
                        Text("↓ ${w.minTemp}°", fontSize = 14.sp, color = Color.White)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(getCurrentDate(), fontSize = 14.sp, color = Color.White)

                    Spacer(modifier = Modifier.height(24.dp))

//                    DetailsCard(
//                        WeatherData(
//                            city = city,
//                            temperature = w.temperature,
//                            condition = w.condition,
//                            maxTemp = w.maxTemp,
//                            minTemp = w.minTemp,
//                            feelsLike = w.feelsLike,
//                            humidity = w.humidity,
//                            windSpeed = w.windSpeed,
//                            pressure = w.pressure
//                        )
//                    )

                    Spacer(modifier = Modifier.height(24.dp))

//                    ForecastSection(forecast)

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
fun TopBar(city: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.LocationOn, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text(city, fontSize = 18.sp, fontWeight = FontWeight.Medium, color = Color.White)
        }

        IconButton(onClick = { }) {
            Icon(Icons.Default.Search, contentDescription = null, tint = Color.White, modifier = Modifier.size(24.dp))
        }
    }
}

@Composable
fun WeatherIcon(condition: String) {
    val icon = when {
        condition.contains("дождь", true) -> "🌧️"
        condition.contains("облачно", true) -> "☁️"
        condition.contains("ясно", true) -> "☀️"
        condition.contains("снег", true) -> "❄️"
        else -> "🌡️"
    }
    Text(icon, fontSize = 64.sp)
}

//@Composable
//fun DetailsCard(weather: WeatherData) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(horizontal = 16.dp),
//        shape = RoundedCornerShape(16.dp),
//        colors = CardDefaults.cardColors(containerColor = Color(0xCC2C3E50))
//    ) {
//        Column(modifier = Modifier.padding(16.dp)) {
//            Text("ПОДРОБНЕЕ", fontSize = 12.sp, color = Color.White.copy(alpha = 0.7f))
//            Spacer(modifier = Modifier.height(12.dp))
//            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
//                DetailItem("🌡️", "Ощущается", "${weather.feelsLike}°")
//                DetailItem("💧", "Влажность", "${weather.humidity}%")
//                DetailItem("💨", "Ветер", "${weather.windSpeed} м/с")
//                DetailItem("📊", "Давление", "${weather.pressure} мм")
//            }
//        }
//    }
//}

@Composable
fun DetailItem(icon: String, label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(icon, fontSize = 24.sp)
        Spacer(Modifier.height(4.dp))
        Text(label, fontSize = 11.sp, color = Color.White.copy(alpha = 0.7f))
        Text(value, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White)
    }
}

//@Composable
//fun ForecastSection(forecast: List<DailyForecast>) {
//    Column(modifier = Modifier.fillMaxWidth()) {
//        Text(
//            text = "Прогноз на неделю",
//            modifier = Modifier.padding(horizontal = 16.dp),
//            fontSize = 16.sp,
//            fontWeight = FontWeight.Bold,
//            color = Color.White
//        )
//        Spacer(Modifier.height(12.dp))
//        LazyRow(
//            modifier = Modifier.fillMaxWidth(),
//            contentPadding = PaddingValues(horizontal = 12.dp),
//            horizontalArrangement = Arrangement.spacedBy(10.dp)
//        ) {
//            items(forecast) { day -> ForecastCard(day) }
//        }
//    }
//}

//@Composable
//fun ForecastCard(forecast: DailyForecast) {
//    Card(
//        modifier = Modifier.width(80.dp).height(110.dp),
//        shape = RoundedCornerShape(12.dp),
//        colors = CardDefaults.cardColors(containerColor = Color(0xCC2C3E50))
//    ) {
//        Column(
//            modifier = Modifier.fillMaxSize(),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center
//        ) {
//            Text(forecast.day, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White)
//            Spacer(Modifier.height(8.dp))
//            Text(forecast.icon, fontSize = 28.sp)
//            Spacer(Modifier.height(6.dp))
//            Text("${forecast.max}°", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
//            Text("${forecast.min}°", fontSize = 12.sp, color = Color.White.copy(alpha = 0.7f))
//        }
//    }
//}

fun getCurrentDate(): String {
    val dateFormat = SimpleDateFormat("EEEE, d MMMM", Locale("ru"))
    return dateFormat.format(Date()).replaceFirstChar { it.uppercase(Locale("ru")) }
}
data class WeatherData(
    val city: String,
    val temperature: Int,
    val condition: String,
    val maxTemp: Int,
    val minTemp: Int,
    val feelsLike: Int,
    val humidity: Int,
    val windSpeed: Double,
    val pressure: Int
)
data class DailyForecast(
    val day: String,
    val icon: String,
    val max: Int,
    val min: Int
)