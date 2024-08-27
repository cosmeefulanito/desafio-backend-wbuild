package com.desafio.wbuild.service

import com.desafio.wbuild.client.*
import com.desafio.wbuild.model.*
import kotlinx.coroutines.runBlocking
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.Test
import kotlin.test.assertEquals

@SpringBootTest
class WeatherServiceTest {

    @InjectMocks
    lateinit var weatherService: WeatherService

    @Mock
    lateinit var weatherClient: WeatherClient

    lateinit var weatherResponse: WeatherResponse


    @Test
    fun `should return weather data for a valid city`() {
        weatherResponse = WeatherResponse(
            name = "Chile",
            main = Main(
                temp = 15.5f,
                humidity = 80
            ),
            weather = listOf(
                WeatherDescription(
                    description = "Clear sky"
                )
            ),
            wind = Wind(
                speed = 4.8f
            )

        )
        runBlocking {
            Mockito.`when`(weatherClient.getWeather("Chile")).thenReturn(weatherResponse)
            val weather = weatherService.getWeather("Chile")
            assertEquals("Chile", weather.ciudad)
        }
    }

}