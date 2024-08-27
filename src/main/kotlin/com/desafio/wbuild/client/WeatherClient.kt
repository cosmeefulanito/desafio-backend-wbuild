package com.desafio.wbuild.client

import com.desafio.wbuild.exception.*
import com.desafio.wbuild.model.WeatherResponse
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Component
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse


@Component
class WeatherClient {

    @Value("\${openweathermap.api.url}")
    lateinit var apiUrl: String

    @Value("\${openweathermap.api.key}")
    lateinit var apiKey: String

    var httpClient: HttpClient = HttpClient.newBuilder().build()
    private val objectMapper = jacksonObjectMapper().apply { configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false) }

    @Cacheable("weatherCache")
    fun getWeather(city: String): WeatherResponse {

        val request = HttpRequest.newBuilder()
            .uri(URI.create("$apiUrl?q=$city&appid=$apiKey&units=metric&lang=es"))
            .GET()
            .build()

            val response = httpClient.send(request, HttpResponse.BodyHandlers.ofString())
            return when (response.statusCode()){
                200 -> {objectMapper.readValue(response.body())}
                400 -> throw BadRequestException("Solicitud incorrecta: ${response.body()}")
                404 -> throw CityNotFoundException("Ciudad no encontrada: $city")
                429 -> throw RateLimitExceededException("Limite de solicitudes excedido")
                in 500..599 -> throw ApiServiceException("Error del servicio de API: ${response.statusCode()}")
                else -> throw RuntimeException("Error inesperado: ${response.statusCode()} - ${response.body()}")
            }
    }
}
