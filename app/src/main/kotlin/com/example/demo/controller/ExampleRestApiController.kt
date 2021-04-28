package com.example.demo.controller

import com.example.demo.framework.ApiVersion
import com.example.demo.framework.RestApiController
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@RestApiController
@RequestMapping("examples")
class ExampleRestApiController {

    // @ApiVersion(1) // Defaults to 1 when missing.
    @GetMapping("number")
    fun numberV1(): Int {
        return 1
    }

    @ApiVersion(2)
    @GetMapping("number")
    fun numberV2(): String {
        return "11111111111111111111111111111111111111111111111111111111111111"
    }

    @ApiVersion(2)
    @GetMapping("custom", produces = ["application/json", "!application/xml"])
    fun custom(): String {
        return "Custom"
    }

}
