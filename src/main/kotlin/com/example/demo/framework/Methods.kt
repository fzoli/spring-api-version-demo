package com.example.demo.framework

import org.springframework.core.annotation.AnnotationUtils
import java.lang.reflect.Method

fun Method.extractApiVersion(): Int? {
    val apiController = AnnotationUtils.findAnnotation(declaringClass, RestApiController::class.java) != null
    if (!apiController) {
        return null
    }
    return AnnotationUtils.findAnnotation(this, ApiVersion::class.java)?.version ?: 1
}
