package com.desafio.wbuild.controller

import com.desafio.wbuild.exception.InvalidCityException
import com.desafio.wbuild.model.Weather
import com.desafio.wbuild.service.WeatherService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class WeatherController(private val weatherService: WeatherService) {

    @GetMapping("/weather")
    fun weather(@RequestParam city: String): ResponseEntity<Weather> {
        if(city.isNullOrBlank()){
            throw InvalidCityException("El parámetro ciudad es inválido o se encuentra vacío")
        }
        return ResponseEntity.ok(weatherService.getWeather(city))
    }
}


