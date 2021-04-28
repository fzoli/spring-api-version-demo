package com.example.demo.feign

import com.example.demo.framework.ApiVersion
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping

@FeignClient(
    name = "example",
    configuration = [ClientConfiguration::class],
    url = "\${example.service.url}"
)
interface ExampleClient {

    // @ApiVersion(1) // Default
    @GetMapping("/examples/number")
    fun numberV1(): Int

    @ApiVersion(2)
    @GetMapping("/examples/number")
    fun numberV2(): String

    @ApiVersion(5)
    @GetMapping("/examples/number")
    fun numberV5(): String

}
