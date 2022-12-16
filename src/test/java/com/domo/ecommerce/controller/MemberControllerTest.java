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
    @DisplayName("회원가입 성공")
    void signUpSuccess() throws Exception {
        //given
        String memberId = "test123";
        String password = "1q2w3e4r!@";
        String name = "홍길동";
        String tel = "010-1111-1111";
        String addressCode = "63309";
        String address = "제주특별자치도 제주시";
        String addressDetail = "1층";
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
    @DisplayName("로그인 성공")
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
    @DisplayName("로그아웃 성공")
    void logoutSuccess() throws Exception {
        //given

        //when
        //then
        mockMvc.perform(get("/members/logout"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("회원가입 실패 - memberId validation 실패")
    void signUpFailValidMemberId() throws Exception {
        //given
        String memberId = " ";
        String password = "1q2w3e4r!@";
        String name = "홍길동";
        String tel = "010-1111-1111";
        String addressCode = "63309";
        String address = "제주특별자치도 제주시";
        String addressDetail = "1층";
        MemberSignUp.Request request = new MemberSignUp.Request(
                memberId, password, name, tel, addressCode, address, addressDetail
        );
        //when
        //then
        mockMvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.msg").value("아이디는 영어, 숫자 를 포함하여 6~20자리 이내로 입력해 주세요."))
                .andExpect(jsonPath("$.errorCode").value("MethodArgumentNotValidException"))
                .andDo(print());
    }

    @Test
    @DisplayName("회원가입 실패 - password validation 실패")
    void signUpFailValidPassword() throws Exception {
        //given
        String memberId = "test123";
        String password = " ";
        String name = "홍길동";
        String tel = "010-1111-1111";
        String addressCode = "63309";
        String address = "제주특별자치도 제주시";
        String addressDetail = "1층";
        MemberSignUp.Request request = new MemberSignUp.Request(
                memberId, password, name, tel, addressCode, address, addressDetail
        );
        //when
        //then
        mockMvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.msg").value("비밀번호는 영어, 숫자, 특수문자를 포함해서 8~16자리 이내로 입력해 주세요."))
                .andExpect(jsonPath("$.errorCode").value("MethodArgumentNotValidException"))
                .andDo(print());
    }

    @Test
    @DisplayName("회원가입 실패 - name validation 실패")
    void signUpFailValidName() throws Exception {
        //given
        String memberId = "test123";
        String password = "1q2w3e4r!@";
        String name = "";
        String tel = "010-1111-1111";
        String addressCode = "63309";
        String address = "제주특별자치도 제주시";
        String addressDetail = "1층";
        MemberSignUp.Request request = new MemberSignUp.Request(
                memberId, password, name, tel, addressCode, address, addressDetail
        );
        //when
        //then
        mockMvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.msg").value("이름을 정확하게 입력해 주세요."))
                .andExpect(jsonPath("$.errorCode").value("MethodArgumentNotValidException"))
                .andDo(print());
    }

    @Test
    @DisplayName("회원가입 실패 - tel validation 실패")
    void signUpFailValidTel() throws Exception {
        //given
        String memberId = "test123";
        String password = "1q2w3e4r!@";
        String name = "홍길동";
        String tel = "";
        String addressCode = "63309";
        String address = "제주특별자치도 제주시";
        String addressDetail = "1층";
        MemberSignUp.Request request = new MemberSignUp.Request(
                memberId, password, name, tel, addressCode, address, addressDetail
        );
        //when
        //then
        mockMvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.msg").value("전화번호는 010-0000-0000 형식으로 입력해 주세요."))
                .andExpect(jsonPath("$.errorCode").value("MethodArgumentNotValidException"))
                .andDo(print());
    }

    @Test
    @DisplayName("회원가입 실패 - addressCode validation 실패")
    void signUpFailValidAddressCode() throws Exception {
        //given
        String memberId = "test123";
        String password = "1q2w3e4r!@";
        String name = "홍길동";
        String tel = "010-1111-1111";
        String addressCode = "";
        String address = "제주특별자치도 제주시";
        String addressDetail = "1층";
        MemberSignUp.Request request = new MemberSignUp.Request(
                memberId, password, name, tel, addressCode, address, addressDetail
        );
        //when
        //then
        mockMvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.msg").value("우편번호는 숫자 5자리를 입력해 주세요."))
                .andExpect(jsonPath("$.errorCode").value("MethodArgumentNotValidException"))
                .andDo(print());
    }
}