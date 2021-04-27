package com.example.demo.feign

import com.example.demo.framework.ApiVersion
import feign.RequestInterceptor
import feign.RequestTemplate
import org.springframework.core.annotation.AnnotationUtils
import java.lang.reflect.Method

class ApiVersionInterceptor : RequestInterceptor {

    override fun apply(requestTemplate: RequestTemplate) {
        val apiVersion = requestTemplate.methodMetadata().method().extractApiVersion()
        if (apiVersion != null) {
            requestTemplate.removeHeader("Accept")
            requestTemplate.header("Accept", "application/vnd.api.v$apiVersion+json")
        } else {
            if (!requestTemplate.headers().containsKey("Accept")) {
                requestTemplate.header("Accept", "application/vnd.api.v1+json")
            }
        }
    }

    private fun Method.extractApiVersion(): Int? {
        return AnnotationUtils.findAnnotation(this, ApiVersion::class.java)?.version
    }

}
