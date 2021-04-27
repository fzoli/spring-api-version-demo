package com.example.demo.controller

import com.example.demo.error.ErrorMessage
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

/**
 * @see ExampleRestController
 */
class ExampleRestControllerTest : BaseTest() {

    @Test
    fun `undefined version`() {
        accept = "application/json"
        val response = restTemplate.getForEntity("$baseUrl/examples/hello", String::class.java)
        Assertions.assertEquals("Hello", response.body)
    }

    @Test
    fun `default API version`() {
        accept = "application/vnd.api.v1+json"
        val response = restTemplate.getForEntity("$baseUrl/examples/hello", String::class.java)
        Assertions.assertEquals("Hello", response.body)
    }

    @Test
    fun `unexpected API version`() {
        accept = "application/vnd.api.v2+json"
        val response = restTemplate.getForEntity("$baseUrl/examples/hello", ErrorMessage::class.java)
        Assertions.assertEquals("Unexpected API version", response.body?.error)
    }

    @Test
    fun `multiple header`() {
        accepts = listOf("application/vnd.api.v1+json", "application/vnd.api.v2+json")
        val response = restTemplate.getForEntity("$baseUrl/examples/hello", ErrorMessage::class.java)
        Assertions.assertEquals("Only one Accept header can be defined", response.body?.error)
    }

}
