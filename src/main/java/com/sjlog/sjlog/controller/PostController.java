package com.sjlog.sjlog.controller;

import com.sjlog.sjlog.domain.Post;
import com.sjlog.sjlog.request.PostCreate;
import com.sjlog.sjlog.response.PostResponse;
import com.sjlog.sjlog.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

/*
    @PostMapping("/posts")
    public String post(@RequestParam String title, @RequestParam String content) {

      log.info("title={}, content={}",title, content);
        return "hello world";
    }
*/

/*
    @PostMapping("/posts")
    public String post(@RequestParam Map<String, String> params) {

        log.info("params={}", params);
        return "hello world";
    }*/

/*
    @PostMapping("/posts")
    public String post( PostCreate params) {
        log.info("params = {}", params.toString());
        return "hello world";
    }

*/

    // 글 등록
    @PostMapping("/posts")
    public void post(@RequestBody @Valid PostCreate request) {

        postService.write(request);
    }

    // 단건 조회
    @GetMapping("/posts/{postId}")
    public PostResponse get(@PathVariable Long postId) {

        return postService.get(postId);
    }

    // 여러 건 조회
    @GetMapping("/posts")
    public List<PostResponse> getList() {
        return postService.getList();
    }
}
