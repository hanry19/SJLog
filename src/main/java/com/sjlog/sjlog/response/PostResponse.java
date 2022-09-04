package com.sjlog.sjlog.response;

import com.sjlog.sjlog.domain.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.result.UpdateCountOutput;

/**
 * 서비스 정책에 맞는 클래스
 * */

@Getter
public class PostResponse {

    private Long id;
    private String title;
    private String content;

    // 생성자 오버로딩
    public PostResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
    }

    @Builder
    public PostResponse(Long id, String title, String content) {
        this.id = id;
        this.title = title.substring(0, Math.min(title.length(), 10));
        this.content = content;
    }
}
