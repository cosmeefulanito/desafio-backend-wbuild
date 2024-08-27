package com.desafio.wbuild.exception

import com.desafio.wbuild.model.Response
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class WeatherExceptionHandler {

    @ExceptionHandler(RuntimeException::class)
    fun handleRuntimeException(exc: RuntimeException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exc.message)
    }

    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequestException(exc: BadRequestException): ResponseEntity<Response>{
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            Response(
                code = HttpStatus.BAD_REQUEST.value(),
                error = ErrorResponse(
                    descripcion = exc.localizedMessage,
                    tipo = BadRequestException::class.simpleName
                )
            )
        )
    }

    @ExceptionHandler(CityNotFoundException::class)
    fun handleCityNotFoundException(exc: CityNotFoundException): ResponseEntity<Response>{
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            Response(
                code = HttpStatus.NOT_FOUND.value(),
                error = ErrorResponse(
                    descripcion = exc.message,
                    tipo = CityNotFoundException::class.simpleName,
                ),
            )
        )
    }

    @ExceptionHandler(RateLimitExceededException::class)
    fun handleRateLimitExceededException(exc: RateLimitExceededException): ResponseEntity<Response>{
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(
            Response(
                code = HttpStatus.TOO_MANY_REQUESTS.value(),
                error = ErrorResponse(
                    descripcion = exc.message,
                    tipo= RateLimitExceededException::class.simpleName
                )
            )
        )
    }

    @ExceptionHandler(ApiServiceException::class)
    fun handleApiServiceException(exc: ApiServiceException): ResponseEntity<Response>{
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
            Response(
                code = HttpStatus.INTERNAL_SERVER_ERROR.value(),
                error = ErrorResponse(
                    descripcion = exc.message,
                    tipo = ApiServiceException::class.simpleName
                )
            )
        )
    }

    @ExceptionHandler(InvalidCityException::class)
    fun handleInvalidCityException(exc: InvalidCityException): ResponseEntity<Response>{
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            Response(
                code = HttpStatus.BAD_REQUEST.value(),
                error = ErrorResponse(
                    descripcion = exc.message,
                    tipo = InvalidCityException::class.simpleName
                )
            )
        )
    }


}