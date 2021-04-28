package com.example.demo.handler

import com.example.demo.error.ErrorMessage
import com.example.demo.error.ErrorResolver
import com.example.demo.error.ResolvedError
import com.example.demo.exception.UnsupportedApiVersionException
import com.example.demo.framework.isApiVersionException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.HttpMediaTypeNotAcceptableException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.lang.reflect.UndeclaredThrowableException

@ControllerAdvice
class DemoExceptionHandler @Autowired constructor(
    private val errorResolver: ErrorResolver
) : ResponseEntityExceptionHandler() {

    @ExceptionHandler(UndeclaredThrowableException::class)
    fun handleGeneralError(e: UndeclaredThrowableException, request: WebRequest): ResponseEntity<Any> {
        return if (e.cause is Exception) {
            handleGeneralError(e.cause as Exception, request)
        } else {
            handleGeneralError(e as Exception, request)
        }
    }

    @ExceptionHandler(Exception::class)
    fun handleGeneralError(ex: Exception, request: WebRequest): ResponseEntity<Any> {
        return handleExceptionInternal(resolveError(ex), request)
    }

    override fun handleHttpMediaTypeNotAcceptable(
        ex: HttpMediaTypeNotAcceptableException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        if (ex.isApiVersionException()) {
            return handleExceptionInternal(resolveError(UnsupportedApiVersionException()), request)
        }
        return super.handleHttpMediaTypeNotAcceptable(ex, headers, status, request)
    }

    private fun handleExceptionInternal(resolvedErrorMessage: ResolvedError, request: WebRequest): ResponseEntity<Any> {
        return handleExceptionInternal(
            resolvedErrorMessage.exception,
            resolvedErrorMessage.errorMessage,
            resolvedErrorMessage.headers,
            resolvedErrorMessage.httpStatus,
            request
        )
    }

    override fun handleExceptionInternal(
        ex: Exception,
        body: Any?,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        if (body is ErrorMessage) {
            return super.handleExceptionInternal(ex, body, headers, status, request)
        }
        val resolvedErrorMessage = resolveError(ex)
        return handleExceptionInternal(
            resolvedErrorMessage.exception,
            resolvedErrorMessage.errorMessage,
            resolvedErrorMessage.headers,
            resolvedErrorMessage.httpStatus,
            request
        )
    }

    private fun resolveError(ex: Exception): ResolvedError {
        return errorResolver.resolveError(ex)
    }

}
