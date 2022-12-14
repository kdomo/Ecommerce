package com.domo.ecommerce.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.domo.ecommerce.configuration.RedisConfiguration;
import com.domo.ecommerce.dto.member.MemberLogin;
import com.domo.ecommerce.dto.member.MemberLogin.Request;
import com.domo.ecommerce.dto.member.MemberSignUp;
import com.domo.ecommerce.exception.GlobalExceptionHandler;
import com.domo.ecommerce.service.MemberService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;


//@ComponentScan(basePackages = "com.domo.ecommerce.controller")
@ContextConfiguration(classes = {RedisConfiguration.class, MemberController.class, GlobalExceptionHandler.class})
@WebMvcTest(MemberController.class)
class MemberControllerTest {
    @MockBean
    private MemberService memberService;

    @MockBean
    private MockHttpSession session;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("???????????? ??????")
    void signUpSuccess() throws Exception {
        //given
        String memberId = "test123";
        String password = "1q2w3e4r!@";
        String name = "?????????";
        String tel = "010-1111-1111";
        String addressCode = "63309";
        String address = "????????????????????? ?????????";
        String addressDetail = "1???";
        MemberSignUp.Request request = new MemberSignUp.Request(
                memberId, password, name, tel, addressCode, address, addressDetail
        );
        //when
        //then
        mockMvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @DisplayName("????????? ??????")
    void loginSuccess() throws Exception {
        //given
        String memberId = "test123";
        String password = "1q2w3e4r!@";
        MemberLogin.Request request = new Request(memberId, password);
        //when
        //then
        mockMvc.perform(post("/members/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .session(session))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("???????????? ??????")
    void logoutSuccess() throws Exception {
        //given

        //when
        //then
        mockMvc.perform(get("/members/logout"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("???????????? ?????? - memberId validation ??????")
    void signUpFailValidMemberId() throws Exception {
        //given
        String memberId = " ";
        String password = "1q2w3e4r!@";
        String name = "?????????";
        String tel = "010-1111-1111";
        String addressCode = "63309";
        String address = "????????????????????? ?????????";
        String addressDetail = "1???";
        MemberSignUp.Request request = new MemberSignUp.Request(
                memberId, password, name, tel, addressCode, address, addressDetail
        );
        //when
        //then
        mockMvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.msg").value("???????????? ??????, ?????? ??? ???????????? 6~20?????? ????????? ????????? ?????????."))
                .andExpect(jsonPath("$.errorCode").value("MethodArgumentNotValidException"))
                .andDo(print());
    }

    @Test
    @DisplayName("???????????? ?????? - password validation ??????")
    void signUpFailValidPassword() throws Exception {
        //given
        String memberId = "test123";
        String password = " ";
        String name = "?????????";
        String tel = "010-1111-1111";
        String addressCode = "63309";
        String address = "????????????????????? ?????????";
        String addressDetail = "1???";
        MemberSignUp.Request request = new MemberSignUp.Request(
                memberId, password, name, tel, addressCode, address, addressDetail
        );
        //when
        //then
        mockMvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.msg").value("??????????????? ??????, ??????, ??????????????? ???????????? 8~16?????? ????????? ????????? ?????????."))
                .andExpect(jsonPath("$.errorCode").value("MethodArgumentNotValidException"))
                .andDo(print());
    }

    @Test
    @DisplayName("???????????? ?????? - name validation ??????")
    void signUpFailValidName() throws Exception {
        //given
        String memberId = "test123";
        String password = "1q2w3e4r!@";
        String name = "";
        String tel = "010-1111-1111";
        String addressCode = "63309";
        String address = "????????????????????? ?????????";
        String addressDetail = "1???";
        MemberSignUp.Request request = new MemberSignUp.Request(
                memberId, password, name, tel, addressCode, address, addressDetail
        );
        //when
        //then
        mockMvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.msg").value("????????? ???????????? ????????? ?????????."))
                .andExpect(jsonPath("$.errorCode").value("MethodArgumentNotValidException"))
                .andDo(print());
    }

    @Test
    @DisplayName("???????????? ?????? - tel validation ??????")
    void signUpFailValidTel() throws Exception {
        //given
        String memberId = "test123";
        String password = "1q2w3e4r!@";
        String name = "?????????";
        String tel = "";
        String addressCode = "63309";
        String address = "????????????????????? ?????????";
        String addressDetail = "1???";
        MemberSignUp.Request request = new MemberSignUp.Request(
                memberId, password, name, tel, addressCode, address, addressDetail
        );
        //when
        //then
        mockMvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.msg").value("??????????????? 010-0000-0000 ???????????? ????????? ?????????."))
                .andExpect(jsonPath("$.errorCode").value("MethodArgumentNotValidException"))
                .andDo(print());
    }

    @Test
    @DisplayName("???????????? ?????? - addressCode validation ??????")
    void signUpFailValidAddressCode() throws Exception {
        //given
        String memberId = "test123";
        String password = "1q2w3e4r!@";
        String name = "?????????";
        String tel = "010-1111-1111";
        String addressCode = "";
        String address = "????????????????????? ?????????";
        String addressDetail = "1???";
        MemberSignUp.Request request = new MemberSignUp.Request(
                memberId, password, name, tel, addressCode, address, addressDetail
        );
        //when
        //then
        mockMvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.msg").value("??????????????? ?????? 5????????? ????????? ?????????."))
                .andExpect(jsonPath("$.errorCode").value("MethodArgumentNotValidException"))
                .andDo(print());
    }
}