package com.desafio.wbuild.model

import com.desafio.wbuild.exception.ErrorResponse

data class WeatherResponse(
    val name: String,
    val main: Main,
    val weather: List<WeatherDescription>,
    val wind: Wind,
)

data class Main (
    val temp: Float,
    val humidity: Int
)

data class WeatherDescription(
    val description: String
)

data class Wind(
    val speed: Float
)

data class Response(
    val code: Int,
    val error: ErrorResponse? ,
)