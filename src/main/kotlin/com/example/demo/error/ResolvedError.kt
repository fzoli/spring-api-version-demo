package com.example.demo.error

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus

data class ResolvedError(
    val exception: Exception,
    val errorMessage: ErrorMessage,
    val httpStatus: HttpStatus,
    val headers: HttpHeaders
)
