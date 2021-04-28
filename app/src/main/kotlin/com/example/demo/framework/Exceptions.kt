package com.example.demo.framework

import org.springframework.web.HttpMediaTypeNotAcceptableException

fun HttpMediaTypeNotAcceptableException.isApiVersionException(): Boolean {
    val apiVersions = supportedMediaTypes.mapNotNull { mediaType -> ApiVersionParser.parseApiVersion(mediaType) }
    return apiVersions.size == supportedMediaTypes.size
}
