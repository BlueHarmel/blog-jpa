package com.estsoft.blogjpa.controller;

import com.estsoft.blogjpa.domain.User;
import com.estsoft.blogjpa.dto.AddUserRequest;
import com.estsoft.blogjpa.repository.UserRepository;
import com.estsoft.blogjpa.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest2 {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    WebApplicationContext ac;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void mockMvcSetUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ac).build();
    }

    @Test
    void signup() throws Exception {
        // given : 회원가입에 필요한 정보 초기화
        AddUserRequest request = new AddUserRequest("mock_email", "mock_pw");

        // when : POST /user
        ResultActions response = mockMvc.perform(post("/user")
                .param("email", request.getEmail())
                .param("password", request.getPassword()));

        // then : 호출 결과 HTTP Status code 300, user 저장 여부 검증
        response.andExpect(status().is3xxRedirection());

        User byEmail = userRepository.findByEmail(request.getEmail()).orElseThrow();
        Assertions.assertNotNull(byEmail);
    }

//    @DisplayName("멤버 목록 조회에 성공한다")
//    @Test
//    public void getAllUsers() throws Exception {
//        // given
//        String url = "/user";
//        User savedUser = UserRepository.save(new User("1L", "홍길동"));
//
//        // when
//        ResultActions resultActions = mockMvc.perform(get(url)		// 1
//                .accept(MediaType.APPLICATION_JSON));		// 2
//
//        // then
//        resultActions.andExpect(status().isOk())    // 3
//                // 4. 응답의 0번째 값이 DB에 저장한 값과 같은지 검증
//                .andExpect((ResultMatcher) jsonPath("$[0].id").value(savedUser.getId()))
//                .andExpect((ResultMatcher) jsonPath("$[0].name").value(savedUser.getEmail()));
//    }

}