package com.desafio.wbuild.model

data class Weather (
    val ciudad: String,
    val temperatura: Float,
    val descripcionClima: String,
    val humedad: Int,
    val velocidadViento: Float
)