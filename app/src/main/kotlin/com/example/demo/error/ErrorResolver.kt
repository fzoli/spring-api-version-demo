package com.example.demo.error

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component

@Component
class ErrorResolver {

    fun resolveError(ex: Exception): ResolvedError {
        return ResolvedError(
            exception = ex,
            errorMessage = ErrorMessage(error = ex.message ?: ""),
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
            headers = HttpHeaders.EMPTY
        )
    }

}
