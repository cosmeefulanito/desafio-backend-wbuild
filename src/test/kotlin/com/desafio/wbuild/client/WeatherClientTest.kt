package com.desafio.wbuild.client

import com.desafio.wbuild.exception.ApiServiceException
import com.desafio.wbuild.exception.CityNotFoundException
import com.desafio.wbuild.exception.InvalidCityException
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.springframework.test.util.ReflectionTestUtils
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class WeatherClientTest {

    private lateinit var weatherClient: WeatherClient

    @Mock
    private lateinit var mockHttpClient: HttpClient

    @Mock
    private lateinit var mockHttpResponse: HttpResponse<String>

    private val objectMapper = jacksonObjectMapper().apply {
        configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    }

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        weatherClient = WeatherClient()
        ReflectionTestUtils.setField(weatherClient, "apiUrl", "https://api.testweather.org/data/weather")
        ReflectionTestUtils.setField(weatherClient, "apiKey", "your-api-key")
        weatherClient.httpClient = mockHttpClient
    }

    @Test
    fun `test getWeather returns WeatherResponse on success`() {
        val city = "Santiago"
        val jsonResponse = """{"name": "Santiago", "weather":[{"description":"clear sky"}],"main":{"temp":22.0,"humidity":45},"wind":{"speed":5.0}}"""
        `when`(mockHttpResponse.statusCode()).thenReturn(200)
        `when`(mockHttpResponse.body()).thenReturn(jsonResponse)
        `when`(mockHttpClient.send(any(HttpRequest::class.java), eq(HttpResponse.BodyHandlers.ofString())))
            .thenReturn(mockHttpResponse)

        val result = weatherClient.getWeather(city)

        assertNotNull(result)
        assertEquals("clear sky", result.weather[0].description)
        assertEquals(22.0f, result.main.temp)
        assertEquals(45, result.main.humidity)
        assertEquals(5.0f, result.wind.speed)
    }

    @Test
    fun `test getWeather throws InvalidCityException on empty city`() {
        val exception = assertThrows<InvalidCityException> {
            weatherClient.getWeather("")
        }
        assertEquals("El parámetro ciudad es inválido o se encuentra vacío", exception.message)
    }

    @Test
    fun `test getWeather throws CityNotFoundException on 404 status`() {
        val city = "InvalidCity"
        `when`(mockHttpResponse.statusCode()).thenReturn(404)
        `when`(mockHttpResponse.body()).thenReturn("")
        `when`(mockHttpClient.send(any(HttpRequest::class.java), eq(HttpResponse.BodyHandlers.ofString())))
            .thenReturn(mockHttpResponse)

        val exception = assertThrows<CityNotFoundException> {
            weatherClient.getWeather(city)
        }
        assertEquals("Ciudad no encontrada: $city", exception.message)
    }

    @Test
    fun `test getWeather throws ApiServiceException on 500 status`() {
        val city = "Santiago"
        `when`(mockHttpResponse.statusCode()).thenReturn(500)
        `when`(mockHttpClient.send(any(HttpRequest::class.java), eq(HttpResponse.BodyHandlers.ofString())))
            .thenReturn(mockHttpResponse)

        val exception = assertThrows<ApiServiceException> {
            weatherClient.getWeather(city)
        }
        assertEquals("Error del servicio de API: 500", exception.message)
    }

}