package com.springboot.restapi.springbootrestapiblog.repository;

import com.springboot.restapi.springbootrestapiblog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
