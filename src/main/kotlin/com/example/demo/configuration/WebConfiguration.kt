package com.example.demo.configuration

import com.example.demo.exception.UnsupportedApiVersionException
import com.example.demo.framework.ApiVersionInterceptor
import com.example.demo.handler.DemoRequestMappingHandlerMapping
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping

@Configuration
class WebConfiguration : WebMvcConfigurationSupport() {

    override fun addInterceptors(registry: InterceptorRegistry) {
        super.addInterceptors(registry)
        registry.addInterceptor(ApiVersionInterceptor { _, _ ->
            UnsupportedApiVersionException()
        })
    }

    override fun createRequestMappingHandlerMapping(): RequestMappingHandlerMapping {
        return DemoRequestMappingHandlerMapping()
    }

}
