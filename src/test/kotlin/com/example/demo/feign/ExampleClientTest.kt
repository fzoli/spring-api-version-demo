package com.example.demo.feign

import com.example.demo.DemoApplication
import com.example.demo.exception.UnsupportedApiVersionException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Configuration

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = [DemoApplication::class, ExampleClientTest.FeignConfig::class]
)
class ExampleClientTest {

    @Configuration
    @EnableFeignClients(clients = [ExampleClient::class])
    internal class FeignConfig

    @Autowired
    private lateinit var client: ExampleClient

    @Test
    fun numberV1() {
        Assertions.assertEquals(1, client.numberV1())
    }

    @Test
    fun numberV2() {
        Assertions.assertEquals("11111111111111111111111111111111111111111111111111111111111111", client.numberV2())
    }

    @Test
    fun `unsupported version`() {
        Assertions.assertThrows(UnsupportedApiVersionException::class.java) {
            client.numberV5()
        }
    }

}
