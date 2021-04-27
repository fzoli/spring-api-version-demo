package com.example.demo.feign

import com.fasterxml.jackson.databind.ObjectMapper
import feign.RequestInterceptor
import feign.codec.ErrorDecoder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ClientConfiguration {

    @Bean
    fun errorDecoder(objectMapper: ObjectMapper): ErrorDecoder {
        return ClientErrorDecoder(objectMapper)
    }

    @Bean
    fun apiVersionInterceptor(): RequestInterceptor {
        return ApiVersionInterceptor()
    }

}
