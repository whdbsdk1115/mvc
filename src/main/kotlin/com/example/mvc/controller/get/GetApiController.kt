package com.example.mvc.controller.get

import com.example.mvc.model.http.UserRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController // REST API Controller 동작
@RequestMapping("/api") // http://localhost:8080/api 로 주소 노출

class GetApiController {

    // 현재 사용하는 방법
//    @GetMapping("/hello") // GET http://localhost:8080/api/hello
    @GetMapping(path = ["/hello", "/abcd"]) // 배열로도 지정 가능. 여러 개의 값 가능. http://localhost:8080/api/abcd도 가능.
    fun hello(): String {
        return "hello kotlin"
    }

    // 예전에 사용한 방법
//    @RequestMapping("request-mapping") // GET, POST, PUT 등 제약없이 다 동작 가능함.
    // 제약을 주기 위해 method 지정.
    @RequestMapping(method = [RequestMethod.GET], path = ["/request-mapping"])
    fun requestMapping(): String {
        return "request-mapping"
    }

    @GetMapping("/get-mapping/path-variable/{name}/{age}") // GET http://localhost:8080/api/get-mapping/path-variable/yoonaaaa/23
    fun pathVariable(@PathVariable name: String, @PathVariable age: Int): String {
        println("${name}, ${age}")
        return name+" "+age
    }

    @GetMapping("/get-mapping/path-variable2/{name}/{age}") // GET http://localhost:8080/api/get-mapping/path-variable2/yoonaaaa/23
    fun pathVariable2(@PathVariable(value = "name") _name: String, @PathVariable(name = "age") age: Int): String {
        val name = "kotlin"

        println("${_name}, ${age}")
        return _name+" "+age
    }

    // http://localhost:8080/api/page?key=value&key=value
    // Query parameter
    @GetMapping("/get-mapping/query-param") // /name=yoonaaaa&age=23
    fun queryParam(
        @RequestParam() name: String,
        @RequestParam(value = "age") age: Int
    ): String {
        println("${name}, ${age}")
        return name + " " + age
    }

    // name, age, address, email
    @GetMapping("/get-mapping/query-param/object")
    fun queryParamObject(userRequest: UserRequest): UserRequest {
        println(userRequest)
        return userRequest
    }

    @GetMapping("/get-mapping/query-param/map")
    fun queryParamMap(@RequestParam map: Map<String, Any>): Map<String, Any> {
        println(map)
        val phoneNumber = map.get("phone-number")
        println(phoneNumber)
        return map
    }
}

