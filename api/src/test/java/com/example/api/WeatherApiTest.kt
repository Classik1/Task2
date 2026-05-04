package com.example.api

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import mockwebserver3.MockResponse
import mockwebserver3.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherApiTest {

        private lateinit var server: MockWebServer
        private lateinit var api: WeatherApi

        @Before
        fun setup() {
            server = MockWebServer()
            server.start()

            api = Retrofit.Builder()
                .baseUrl(server.url("/"))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherApi::class.java)
        }

        @After
        fun tearDown() {
            server.close()
        }

        @Test
        fun `getForecast returns correct`() = runBlocking {
            val mockJson = """
        {
            "location": { "name": "Moscow" },
            "current": { 
                "temp_c": 25.0,
                "condition": { "text": "Sunny" },
                "feelslike_c": 27.0,
                "humidity": 40,
                "wind_kph": 10.0,
                "pressure_mb": 1013
            },
            "forecast": { "forecastday": [] }
        }
        """.trimIndent()

            val response = MockResponse.Builder()
                .code(200)
                .body(mockJson)
                .addHeader("Content-Type", "application/json")
                .build()

            server.enqueue(response)

            val result = api.getForecast("fake_key", "Moscow")

            println("""
                Результат теста:
                ${result.location.name}, ${result.current.temp_c}, 
                ${result.current.condition.text}, ${result.current.humidity}
                ${server.takeRequest()}
                
            """.trimIndent())

            assertEquals("Moscow", result.location.name)
            assertEquals(25.0, result.current.temp_c)
            assertEquals("Sunny", result.current.condition.text)
            assertEquals(40, result.current.humidity)
            assertEquals(10.0, result.current.wind_kph)
        }


}

