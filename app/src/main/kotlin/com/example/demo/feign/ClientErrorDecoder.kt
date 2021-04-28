package com.example.demo.feign

import com.example.demo.error.ErrorMessage
import com.example.demo.exception.UnsupportedApiVersionException
import com.example.demo.exception.UnsupportedApiVersionMessage
import com.fasterxml.jackson.databind.ObjectMapper
import feign.Response
import feign.codec.ErrorDecoder
import java.nio.charset.StandardCharsets

class ClientErrorDecoder(private var objectMapper: ObjectMapper) : ErrorDecoder {

    override fun decode(methodKey: String, response: Response): Exception {
        val parser = objectMapper.createParser(response.body().asReader(StandardCharsets.UTF_8))
        val errorMessage = parser.readValueAs(ErrorMessage::class.java)
        return when (errorMessage.error) {
            UnsupportedApiVersionMessage -> UnsupportedApiVersionException()
            else -> Exception("General error")
        }
    }

}
