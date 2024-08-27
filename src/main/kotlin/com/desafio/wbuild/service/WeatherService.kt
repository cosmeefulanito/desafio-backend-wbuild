package com.desafio.wbuild.service

import com.desafio.wbuild.client.WeatherClient
import com.desafio.wbuild.model.Weather
import org.springframework.stereotype.Service

@Service
class WeatherService(private val weatherClient: WeatherClient) {

    fun getWeather(city: String): Weather {
        val response = weatherClient.getWeather(city)

        return Weather(
            ciudad = response.name,
            temperatura = response.main.temp.toFloat(),
            descripcionClima = response.weather[0].description,
            humedad = response.main.humidity,
            velocidadViento = response.wind.speed.toFloat()
        )
    }
}
