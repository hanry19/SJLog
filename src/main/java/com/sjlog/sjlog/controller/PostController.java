package com.sjlog.sjlog.controller;

import com.sjlog.sjlog.request.PostCreate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class PostController {


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
public Map<String, String> post(@RequestBody @Valid PostCreate params)  {

    return Map.of();
}


}
