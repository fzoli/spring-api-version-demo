package com.example.demo.controller

import com.example.demo.error.ErrorMessage
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

/**
 * @see ExampleRestApiController
 */
class ExampleRestApiControllerTest : BaseTest() {

    @Test
    fun `number - version 1`() {
        accept = "application/vnd.api.v1+json"
        val response = restTemplate.getForEntity("$baseUrl/examples/number", Int::class.java)
        Assertions.assertEquals(1, response.body)
    }

    @Test
    fun `number - version 2`() {
        accept = "application/vnd.api.v2+json"
        val response = restTemplate.getForEntity("$baseUrl/examples/number", String::class.java)
        Assertions.assertEquals("11111111111111111111111111111111111111111111111111111111111111", response.body)
    }

    @Test
    fun `number - version 2 with encoding`() {
        accept = "application/vnd.api.v2+json; charset=utf-8"
        val response = restTemplate.getForEntity("$baseUrl/examples/number", String::class.java)
        Assertions.assertEquals("11111111111111111111111111111111111111111111111111111111111111", response.body)
    }

    @Test
    fun `number - version 5 (unsupported version)`() {
        accept = "application/vnd.api.v5+json"
        val response = restTemplate.getForEntity("$baseUrl/examples/number", ErrorMessage::class.java)
        Assertions.assertEquals("Unsupported API version", response.body?.error)
    }

    @Test
    fun `number - unexpected syntax`() {
        accept = "application/unexpected+json"
        val response = restTemplate.getForEntity("$baseUrl/examples/number", ErrorMessage::class.java)
        Assertions.assertEquals("Unsupported API version", response.body?.error)
    }

    @Test
    fun `number - undefined version`() {
        accept = "application/json"
        val response = restTemplate.getForEntity("$baseUrl/examples/number", ErrorMessage::class.java)
        Assertions.assertEquals("Unsupported API version", response.body?.error)
    }

    @Test
    fun `number - match any header`() {
        accept = "text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2"
        val response = restTemplate.getForEntity("$baseUrl/examples/number", ErrorMessage::class.java)
        Assertions.assertEquals("Invalid Accept header", response.body?.error)
    }

    @Test
    fun `number - multiple header`() {
        accepts = listOf("application/vnd.api.v1+json", "application/vnd.api.v2+json")
        val response = restTemplate.getForEntity("$baseUrl/examples/number", ErrorMessage::class.java)
        Assertions.assertEquals("Only one Accept header can be defined", response.body?.error)
    }

    @Test
    fun `custom - version 1 (unsupported version)`() {
        accept = "application/vnd.api.v1+json"
        val response = restTemplate.getForEntity("$baseUrl/examples/custom", ErrorMessage::class.java)
        Assertions.assertEquals("Unsupported API version", response.body?.error)
    }

    @Test
    fun `custom - version 2`() {
        accept = "application/vnd.api.v2+json"
        val response = restTemplate.getForEntity("$baseUrl/examples/custom", String::class.java)
        Assertions.assertEquals("Custom", response.body)
    }

    @Test
    fun `custom - version 5 (unsupported version)`() {
        accept = "application/vnd.api.v5+json"
        val response = restTemplate.getForEntity("$baseUrl/examples/custom", ErrorMessage::class.java)
        Assertions.assertEquals("Unsupported API version", response.body?.error)
    }

    @Test
    fun `custom - undefined version`() {
        accept = "application/json"
        val response = restTemplate.getForEntity("$baseUrl/examples/custom", ErrorMessage::class.java)
        Assertions.assertEquals("Undefined API version", response.body?.error)
    }

}
