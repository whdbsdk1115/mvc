package com.example.mvc.controller.exception

import com.example.mvc.model.http.UserRequest
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.util.LinkedMultiValueMap


// 테스트 시에 필요한 어노테이션만 붙임
@WebMvcTest
@AutoConfigureMockMvc
class ExceptionApiControllerTest {

    @Autowired // 스프링에서 자동으로 mockmvc 관련해서 주입 시켜줌
    lateinit var mockMvc: MockMvc

    @Test
    fun helloTest() {
        mockMvc.perform(
//            MockMvcRequestBuilders.get("/api/exception/hello")
            get("/api/exception/hello")
        ).andExpect (
//            MockMvcResultMatchers.status().isOk
            status().isOk
        ).andExpect (
            MockMvcResultMatchers.content().string("hello")
        ).andDo(
//            MockMvcResultHandlers.print()
            print()
        )
    }

    @Test
    fun getTest() {
        val queryParams = LinkedMultiValueMap<String, String>()
        queryParams.add("name", "yoonaa")
        queryParams.add("age", "23")

        mockMvc.perform(
            get("/api/exception").queryParams(queryParams)
        ).andExpect (
            status().isOk
        ).andExpect(
            content().string("yoonaa 23")
        ).andDo(print())
    }

    @Test
    fun getFailTest() {
        val queryParams = LinkedMultiValueMap<String, String>()
        queryParams.add("name", "yoonaa")
        queryParams.add("age", "9")

        mockMvc.perform(
            get("/api/exception").queryParams(queryParams)
        ).andExpect (
            status().isBadRequest
        ).andExpect(
//            content().string("yoonaa 23")
            content().contentType("application/json")
        ).andExpect(
            jsonPath("\$.result_code").value("FAIL")
        ).andExpect(
            jsonPath("\$.errors[0].field").value("age")
        ).andExpect(
            jsonPath("\$.errors[0].value").value("9")
        ).andDo(print())
    }

    @Test
    fun postTest() {

        val userRequest = UserRequest().apply {
            this.name = "yoonaa"
            this.age = 10
            this.phoneNumber = "010-1111-1111"
            this.address = "경기도 부천시"
            this.email = "yoon@gamail.com"
            this.createdAt = "2022-12-01 14:17:33"
        }

        val json = jacksonObjectMapper().writeValueAsString(userRequest)
        print(json)

        mockMvc.perform(
            post("/api/exception")
                .content(json)
                .contentType("application/json")
                .accept("application/json")
        ).andExpect(
            status().isOk
        ).andExpect(
            jsonPath("\$.name").value("yoonaa")
        ).andExpect(
            jsonPath("\$.age").value("10")
        ).andExpect(
            jsonPath("\$.email").value("yoon@gamail.com")
        ).andExpect(
            jsonPath("\$.name").value("yoonaa")
        ).andDo(print())
    }

    @Test
    fun postFailTest() {

        val userRequest = UserRequest().apply {
            this.name = "yoonaa"
            this.age = -1
            this.phoneNumber = "010-1111-1111"
            this.address = "경기도 부천시"
            this.email = "yoon@gamail.com"
            this.createdAt = "2022-12-01 14:17:33"
        }

        val json = jacksonObjectMapper().writeValueAsString(userRequest)
        print(json)

        mockMvc.perform(
            post("/api/exception")
                .content(json)
                .contentType("application/json")
                .accept("application/json")
        ).andExpect(
            status().isBadRequest
        ).andDo(print())
    }
}