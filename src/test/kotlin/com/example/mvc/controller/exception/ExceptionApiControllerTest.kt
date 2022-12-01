package com.example.mvc.controller.exception

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


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
        ).andExpect {
//            MockMvcResultMatchers.status().isOk
            status().isOk
        }.andExpect (
            MockMvcResultMatchers.content().string("hello")
        ).andDo(
//            MockMvcResultHandlers.print()
            print()
        )
    }
}