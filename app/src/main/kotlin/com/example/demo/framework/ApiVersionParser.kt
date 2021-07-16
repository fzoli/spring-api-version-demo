package com.example.demo.framework

import org.springframework.http.MediaType
import java.util.regex.Pattern

object ApiVersionParser {

    private val jsonPattern = Pattern.compile("application/(.*)json.*")
    private val vendorPattern = Pattern.compile("vnd.api.v(\\d+)\\+")

    fun parseApiVersion(accept: String): Int {
        val jsonMatcher = jsonPattern.matcher(accept)
        if (!jsonMatcher.matches()) {
            throw IllegalArgumentException("Invalid Accept header")
        }
        val vendor = jsonMatcher.group(1)
        if (vendor.isEmpty()) {
            return 1
        }
        val vendorMatcher = vendorPattern.matcher(vendor)
        if (!vendorMatcher.matches()) {
            throw IllegalArgumentException("Undefined API version")
        }
        return vendorMatcher.group(1).toInt()
    }

    fun tryParseApiVersion(accept: String?): Int? {
        if (accept == null) {
            return null
        }
        return try {
            parseApiVersion(accept)
        } catch (ex: Exception) {
            null
        }
    }

    fun parseApiVersion(mediaType: MediaType): Int? {
        return tryParseApiVersion(mediaType.toString())
    }

}
