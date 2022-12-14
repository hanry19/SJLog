package com.sjlog.sjlog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sjlog.sjlog.domain.Post;
import com.sjlog.sjlog.repository.PostRepository;
import com.sjlog.sjlog.request.PostCreate;
import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest // 간단한 웹 테스트는 ㄱㅊ 근데 서비스 어쩌구 저쩌구 많이 붙었을 때는 안됨

@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void clean() {
        // test code가 실행되기 전에 먼저 실행됨
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("/posts 시 헬로 월드 출력")
    void test() throws Exception {
        //given
        PostCreate request = PostCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        String json = objectMapper.writeValueAsString(request);


        //expected
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());

        // test 실행 시 db에 하나가 등록이 됨

    }

    @Test
    @DisplayName("/post 요청시 title 값은 필수다")
    void test2() throws Exception {

        //given
        PostCreate request = PostCreate.builder()
                .content("내용입니다.")
                .build();

        String json = objectMapper.writeValueAsString(request);

        System.out.println("json = " + json);

        //expected
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.title").value("타이틀을 입력해주세요."))
                .andDo(print());
    }

    @Test
    @DisplayName("/post 요청시 db에 값이 저장된다.")
    void test3() throws Exception {
        //given
        PostCreate request = PostCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        String json = objectMapper.writeValueAsString(request);


        //when
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());

        //then
        assertEquals(1L, postRepository.count());

        Post post = postRepository.findAll().get(0);
        assertEquals("제목입니다.", post.getTitle());
        assertEquals("내용입니다.", post.getContent());

    }

    @Test
    @DisplayName("글 1개 조회 ")
    void test4() throws Exception {
        //given
        Post post = Post.builder()
                .title("12345678901234")
                .content("hello")
                .build();
        postRepository.save(post);

        // expected
        mockMvc.perform(get("/posts/{postId}",post.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(post.getId()))
                .andExpect(jsonPath("$.title").value("1234567890"))
                .andExpect(jsonPath("$.content").value("hello"))
                .andDo(print());


    }

    @Test
    @DisplayName("글 여러개 조회 ")
    void test5() throws Exception {
        //given
        Post post1 = Post.builder()
                .title("1234")
                .content("hello")
                .build();
        postRepository.save(post1);

        Post post2 = Post.builder()
                .title("12")
                .content("hi")
                .build();
        postRepository.save(post2);

        // expected
        mockMvc.perform(get("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                /**
                 *  1건 조회 할 떄는 json형태로 넘어오지만 전체 조회 할 땐 list로 넘어옴 [{}, {}....] 그래서
                 * */
                .andExpect(jsonPath("$.length()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].id").value(post1.getId()))
                .andExpect(jsonPath("$[0].title").value("1234"))
                .andExpect(jsonPath("$[0].content").value("hello"))
                .andExpect(jsonPath("$[1].id").value(post2.getId()))
                .andExpect(jsonPath("$[1].title").value("12"))
                .andExpect(jsonPath("$[1].content").value("hi"))
                .andDo(print());


    }
}

