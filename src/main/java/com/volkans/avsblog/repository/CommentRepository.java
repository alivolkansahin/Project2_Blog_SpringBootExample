package com.volkans.avsblog.repository;

import com.volkans.avsblog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    Boolean existsByUserId(Long id);
    List<Comment> findAllByUserId(Long id);
    Boolean existsByPostId(Long id);
    List<Comment> findAllByPostId(Long id);

}
