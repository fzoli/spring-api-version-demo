package com.example.demo.exception

const val UnsupportedApiVersionMessage = "Unsupported API version"

class UnsupportedApiVersionException : RuntimeException(UnsupportedApiVersionMessage)
