package com.example.demo.framework

import org.springframework.web.context.request.async.WebAsyncUtils
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class ApiVersionInterceptor(
    private val exceptionFactory: (requestedApiVersion: Int, apiVersion: Int) -> Exception
) : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (WebAsyncUtils.getAsyncManager(request).hasConcurrentResult()) {
            return true
        }
        val apiVersion = if (handler is HandlerMethod) {
            handler.method.extractApiVersion()
        } else {
            null
        }
        val acceptList = request.getHeaders("Accept").toList()
        if (acceptList.size > 1) {
            throw IllegalArgumentException("Only one Accept header can be defined")
        }
        if (apiVersion == null) {
            val accept = acceptList.firstOrNull()
            val requestedApiVersion = ApiVersionParser.tryParseApiVersion(accept)
            if (requestedApiVersion != null && requestedApiVersion != 1) {
                throw IllegalArgumentException("Unexpected API version")
            }
            return true
        }
        if (acceptList.isEmpty()) {
            throw IllegalArgumentException("Missing Accept header")
        }
        val accept = acceptList.first()
        val requestedApiVersion = ApiVersionParser.parseApiVersion(accept)
        if (requestedApiVersion != apiVersion) {
            throw exceptionFactory(requestedApiVersion, apiVersion)
        }
        return true
    }

}
