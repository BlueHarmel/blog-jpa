package com.estsoft.blogjpa.controller;

import com.estsoft.blogjpa.domain.Article;
import com.estsoft.blogjpa.dto.AddArticleRequest;
import com.estsoft.blogjpa.repository.BlogRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@AutoConfigureMockMvc
class BlogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private BlogRepository blogRepository;

    @BeforeEach
    public void mockMvcSetUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        blogRepository.deleteAll();
    }
    @Test
    public void addArticle() throws Exception {
        // given : 저장하고싶은 블로그 정보
        AddArticleRequest request = new AddArticleRequest("제목", "내용");
        String json = objectMapper.writeValueAsString(request);

        // when : POST /api/articles
        ResultActions resultActions = mockMvc.perform(post("/api/articles").with(user("username").password("password").roles("USER"))
                .content(json)
                .contentType(MediaType.APPLICATION_JSON));

        // then : HttpStatusCode 201 검증, { "title": "제목", "content": "내용"}
        resultActions.andExpect(status().isCreated())
                .andExpect(jsonPath("title").value(request.getTitle()))
                .andExpect(jsonPath("content").value(request.getContent()));
    }

    @DisplayName("블로그 글 전체 조회 성공")
    @Test
    public void testFindAll() throws Exception {
        // given
        final String url = "/api/articles";
        final String title = "제목";
        final String content = "내용";
        Article article = blogRepository.save(new Article(title, content));

        // when
        ResultActions result = mockMvc.perform(get(url));

        // then : 정상적으로 요청이 되었는지 검증
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value(article.getTitle()))
                .andExpect(jsonPath("$[0].content").value(article.getContent()));
    }

    @Test
    void findArticleById() {
    }

    @DisplayName("블로그 글 삭제 성공")
    @Test
    public void testDeleteArticle() throws Exception {
        // given
        final String url = "/api/articles/{id}";
        String title = "제목1";
        String content = "내용1";

        Article article = blogRepository.save(new Article(title, content));
        Long savedId = article.getId();

        // when
        mockMvc.perform(delete(url, savedId)).andExpect(status().isOk());

        // then
        List<Article> afterDeleteList = blogRepository.findAll();
        assertThat(afterDeleteList).isEmpty();
    }

    @Test
    void updateArticle() {
    }
}