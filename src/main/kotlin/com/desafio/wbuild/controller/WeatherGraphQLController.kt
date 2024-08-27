package com.desafio.wbuild.controller

import com.desafio.wbuild.model.Weather
import com.desafio.wbuild.service.WeatherService
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class WeatherGraphQLController(private val weatherService: WeatherService) {

    @QueryMapping("weather")
    fun getWeather(city: String): Weather {
        val weather =  weatherService.getWeather(city)
        return Weather(
            ciudad = weather.ciudad,
            temperatura = weather.temperatura,
            descripcionClima = weather.descripcionClima,
            humedad = weather.humedad,
            velocidadViento = weather.velocidadViento
        )
    }
}