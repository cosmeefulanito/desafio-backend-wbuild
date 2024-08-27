package com.desafio.wbuild.controller

import com.desafio.wbuild.service.WeatherService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/health")
class HealthCheckController(private val weatherService: WeatherService) {

    @GetMapping
    fun checkHealth() : ResponseEntity<String>{
        return try {
            weatherService.getWeather("Tokyo")
            ResponseEntity.ok("API is OK, running")
        } catch (e: Exception){
            ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("API is down")
        }
    }
}