package com.example.demo.handler

import com.example.demo.framework.extractApiVersion
import org.springframework.web.servlet.mvc.method.RequestMappingInfo
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping
import java.lang.reflect.Method

class DemoRequestMappingHandlerMapping : RequestMappingHandlerMapping() {

    override fun registerHandlerMethod(handler: Any, method: Method, mapping: RequestMappingInfo) {
        super.registerHandlerMethod(handler, method, mapping.mutate(method))
    }

    private fun RequestMappingInfo.mutate(method: Method): RequestMappingInfo {
        val apiVersion = method.extractApiVersion() ?: return this
        val produces = producesCondition.expressions.map { it.toString() }.toMutableList().apply {
            add(0, "application/vnd.api.v${apiVersion}+json")
        }
        return mutate()
            .produces(*produces.toTypedArray())
            .build()
    }

}
