package com.volkans.avsblog.repository;

import com.volkans.avsblog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {

    Boolean existsByUserId(Long id);

    List<Post> findAllByUserId(Long id);

    Boolean existsByCategoryId(Long id);

    List<Post> findAllByCategoryId(Long id);

    List<Post> findAllByTitleLikeIgnoreCaseOrContentLikeIgnoreCase(String args1, String args2);

    @Query(value = "select * from posts where category_id = (select category_id from categories where name = ?1)",nativeQuery = true)
    List<Post> findAllPostsByCategoryNameNativeQuery(String name);

    List<Post> findAllByOrderByPublishDateDesc();
}
