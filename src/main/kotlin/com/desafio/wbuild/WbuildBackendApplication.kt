package com.desafio.wbuild

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@EnableCaching
class WbuildBackendApplication

fun main(args: Array<String>) {
	runApplication<WbuildBackendApplication>(*args)
}
