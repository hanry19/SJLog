package com.sjlog.sjlog.controller;

import com.sjlog.sjlog.domain.Post;
import com.sjlog.sjlog.request.PostCreate;
import com.sjlog.sjlog.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

@PostMapping("/posts")
public Map post(@RequestBody @Valid PostCreate request)  {
    Long postId = postService.write(request);

    return Map.of("postId", postId);
}


}
