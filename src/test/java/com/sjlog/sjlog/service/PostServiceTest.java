package com.sjlog.sjlog.service;

import com.sjlog.sjlog.domain.Post;
import com.sjlog.sjlog.repository.PostRepository;
import com.sjlog.sjlog.request.PostCreate;
import com.sjlog.sjlog.response.PostResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void clean() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("글작성")
    void test1() {
        //given
        PostCreate postCreate = PostCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        //when
        postService.write(postCreate);

        //then
        Assertions.assertEquals(1L, postRepository.count());
        Post post = postRepository.findAll().get(0);
        assertEquals("제목입니다.", post.getTitle());
        assertEquals("내용입니다.", post.getContent());
    }

    @Test
    @DisplayName("글 단건 조회 ")
    void test2() {
        //given

        Post requestPost = Post.builder()
                .title("hi")
                .content("hello")
                .build();

        postRepository.save(requestPost);


        //when
        PostResponse response = this.postService.get(requestPost.getId());

        //then
        assertNotNull(response);
        assertEquals(1L, postRepository.count());
        assertEquals("hi", response.getTitle());
        assertEquals("hello", response.getContent());

    }

    @Test
    @DisplayName("글 여러개 조회 ")
    void test3() {
        //given

        postRepository.saveAll(List.of(
                Post.builder()
                        .title("hi1")
                        .content("hello1")
                        .build(),
                Post.builder()
                        .title("hi1")
                        .content("hello1")
                        .build()
        ));

        //when
        List<PostResponse> posts = this.postService.getList();


        //then
        assertEquals(2L, posts.size());

    }

}