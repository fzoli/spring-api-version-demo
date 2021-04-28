package com.example.demo.controller

import com.example.demo.framework.ApiVersion
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("examples")
class ExampleRestController {

    @ApiVersion(1) // It has no effect.
    @GetMapping("hello")
    fun hello(): String {
        return "Hello"
    }

}
