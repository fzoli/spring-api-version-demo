package com.example.demo.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpRequest
import org.springframework.http.client.ClientHttpRequestExecution
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.client.ClientHttpResponse

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BaseTest {

    @Autowired
    private lateinit var testRestTemplate: TestRestTemplate

    @LocalServerPort
    private var port: Int = 0

    val baseUrl: String get() = "http://localhost:$port"

    private val acceptList: MutableList<String> = mutableListOf()

    var accepts: List<String>
        get() {
            return acceptList
        }
        set(value) {
            acceptList.clear()
            acceptList.addAll(value)
        }

    var accept: String?
        get() {
            return acceptList.firstOrNull()
        }
        set(value) {
            acceptList.clear()
            if (value != null) {
                acceptList.add(value)
            }
        }

    val restTemplate: TestRestTemplate
        get() {
            testRestTemplate.restTemplate.interceptors = listOf(
                object : ClientHttpRequestInterceptor {
                    override fun intercept(
                        request: HttpRequest,
                        body: ByteArray,
                        execution: ClientHttpRequestExecution
                    ): ClientHttpResponse {
                        request.headers.remove("Accept")
                        acceptList.forEach { accept ->
                            request.headers.add("Accept", accept)
                        }
                        return execution.execute(request, body)
                    }
                }
            )
            return testRestTemplate
        }

}
