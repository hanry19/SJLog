package com.sjlog.sjlog.repository;

import com.sjlog.sjlog.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
