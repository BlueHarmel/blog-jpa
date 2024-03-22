package com.estsoft.blogjpa.controller;

import com.estsoft.blogjpa.domain.User;
import com.estsoft.blogjpa.dto.AddUserRequest;
import com.estsoft.blogjpa.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    WebApplicationContext ac;

    @Autowired
    UserRepository userRepository;

    @Test
    void signup() {
        //given : 회원가입에 필요한 정보 초기화
        AddUserRequest request = new AddUserRequest("mock_email", "mock_pw");
        userRepository.save(new User(request.getEmail(),request.getPassword()));

        // when : POST /user

        // then : 호출 결과 HTTP
        User byEmail = userRepository.findByEmail(request.getEmail()).orElseThrow();
        Assertions.assertNotNull(byEmail);
    }
}