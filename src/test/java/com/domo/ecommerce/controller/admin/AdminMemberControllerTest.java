package com.domo.ecommerce.controller.admin;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.domo.ecommerce.configuration.RedisConfiguration;
import com.domo.ecommerce.controller.MemberController;
import com.domo.ecommerce.dto.member.MemberLogin;
import com.domo.ecommerce.dto.member.MemberLogin.Request;
import com.domo.ecommerce.exception.GlobalExceptionHandler;
import com.domo.ecommerce.service.admin.AdminMemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@ContextConfiguration(classes = {RedisConfiguration.class, AdminMemberController.class, GlobalExceptionHandler.class})
@WebMvcTest(MemberController.class)
class AdminMemberControllerTest {
    @MockBean
    private AdminMemberService adminMemberService;

    @MockBean
    private MockHttpSession session;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("관리자 로그인 성공")
    void adminLoginSuccess() throws Exception {
        //given
        MemberLogin.Request request = new Request(
                "testAdmin123", "1q2w3e4r!@"
        );

        //when
        //then
        mockMvc.perform(post("/admin/login")
                .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .session(session))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("관리자 로그인 실패 - 아이디 공백 입력")
    void adminLoginFailMemberIdBlank() throws Exception {
        //given
        MemberLogin.Request request = new Request(
                "", "1q2w3e4r!@"
        );

        //when
        //then
        mockMvc.perform(post("/admin/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .session(session))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("관리자 로그인 실패 - 패스워드 공백 입력")
    void adminLoginFailPasswordBlank() throws Exception {
        //given
        MemberLogin.Request request = new Request(
                "testAdmin123", ""
        );

        //when
        //then
        mockMvc.perform(post("/admin/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .session(session))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("관리자 로그아웃 성공")
    void adminLogoutSuccess() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(get("/admin/logout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .session(session))
                .andExpect(status().isOk())
                .andDo(print());
    }
}