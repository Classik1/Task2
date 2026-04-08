package com.example.weatherapp.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.weatherapp.R

@Composable
fun WeatherIcon(condition: String) {

    val iconRes = when {
        condition.contains("rain", true) -> R.drawable.ic_rain
        condition.contains("cloud", true) -> R.drawable.ic_cloud
        condition.contains("clear", true) -> R.drawable.ic_sunny
        condition.contains("snow", true) -> R.drawable.ic_snow
        condition.contains("thunder", true) -> R.drawable.ic_thunder
        condition.contains("mist", true) -> R.drawable.ic_mist
        else -> R.drawable.ic_sunny
    }

    Image(
        painter = painterResource(id = iconRes),
        contentDescription = null,
        modifier = Modifier
            .animateContentSize()
            .size(72.dp)
    )
}