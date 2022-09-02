package com.sjlog.sjlog.service;

import com.sjlog.sjlog.domain.Post;
import com.sjlog.sjlog.repository.PostRepository;
import com.sjlog.sjlog.request.PostCreate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {


    private final PostRepository postRepository;

    public Long write(PostCreate postCreate) {

        // postCreate 를 일반 entity형태로 변환해 줘야함

        Post post = Post.builder()
                .title(postCreate.getTitle())
                .content(postCreate.getContent())
                .build();


         postRepository.save(post);
        return post.getId();
    }
}
