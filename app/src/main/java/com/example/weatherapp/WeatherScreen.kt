package com.example.weatherapp

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.database.getCurrentDate

val citySuggestions = listOf(
    "Berlin", "Munich", "Hamburg", "Frankfurt",
    "London", "Paris", "Rome", "Madrid",
    "New York", "Los Angeles", "Tokyo", "Seoul"
)

@Composable
fun WeatherApp(
    viewModel: WeatherViewModel,
    onLogout: () -> Unit,
    onDetailsClick: () -> Unit,

) {

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.init(context)
    }

    var searchQuery by remember { mutableStateOf("") }
    var isSearching by remember { mutableStateOf(false) }

    val city by viewModel.city.collectAsState()
    val weather by viewModel.weather.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()

    val suggestions = remember(searchQuery) {
        citySuggestions.filter { it.contains(searchQuery, true) }.take(5)
    }

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
                    .background(Color.Black.copy(alpha = 0.35f))) }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(16.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize(tween(300)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (isSearching) {

                    TextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        modifier = Modifier.weight(1f),
                        placeholder = { Text("Введите город") },
                        singleLine = true,
                        shape = RoundedCornerShape(16.dp),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                        keyboardActions = KeyboardActions(
                            onSearch = {
                                if (searchQuery.isNotBlank()) {
                                    viewModel.searchCity(searchQuery, context)
                                    searchQuery = ""
                                    isSearching = false
                                }
                            }
                        )
                    )

                    Spacer(Modifier.width(8.dp))

                    Text(
                        "Отмена",
                        color = Color.White,
                        modifier = Modifier.clickable {
                            isSearching = false
                            searchQuery = ""
                        }
                    )

                } else {
                    Text(
                        city,
                        fontSize = 20.sp,
                        color = Color.White,
                        modifier = Modifier.weight(1f)
                    )

                    IconButton(onClick = { isSearching = true }) {
                        Icon(Icons.Default.Search, null, tint = Color.White)
                    }
                }
            }
            if (isSearching && suggestions.isNotEmpty()) {
                Spacer(Modifier.height(8.dp))
                LazyColumn {
                    items(suggestions) { suggestion ->
                        Text(
                            suggestion,
                            color = Color.White,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.searchCity(searchQuery, context)
                                    searchQuery = ""
                                    isSearching = false
                                }
                                .padding(12.dp)
                        )
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            if (error != null) {
                Text(error!!,
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp))
            }

            if (loading) {
                CircularProgressIndicator(color = Color.White)
            } else {
                weather?.let { w ->

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(w.city, fontSize = 28.sp, color = Color.White)

                        WeatherIcon(w.condition)

                        val animatedTemp by animateFloatAsState(
                            targetValue = w.temperature.toFloat(),
                            animationSpec = tween(800))
                        Text(
                            "${animatedTemp.toInt()}°",
                            fontSize = 96.sp,
                            fontWeight = FontWeight.Light,
                            color = Color.White
                        )

                        Text(w.condition, color = Color.White.copy(0.8f))

                        Text(
                            "Макс: ${w.maxTemp}°  Мин: ${w.minTemp}°",
                            color = Color.White.copy(0.7f)
                        )

                        Spacer(Modifier.height(8.dp))
                        Text(getCurrentDate(), color = Color.White.copy(0.6f))
                        Spacer(Modifier.height(16.dp))

                    }
                }
                Button(
                    onClick = onDetailsClick,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Подробнее")
                }
                Button(
                    onClick = { viewModel.logout {
                        onLogout()
                    }
                              },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Log Out")
                }
            }
        }
    }
}
fun getBackground(condition: String): Int {
    return when {
        condition.contains("rain", true) -> R.drawable.rain
        condition.contains("cloud", true) -> R.drawable.cloud
        condition.contains("clear", true) -> R.drawable.sunny
        condition.contains("snow", true) -> R.drawable.snow
        else -> R.drawable.sunny
    }
}