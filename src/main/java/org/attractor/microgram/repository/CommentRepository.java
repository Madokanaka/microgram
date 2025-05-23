package org.attractor.microgram.repository;

import org.attractor.microgram.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByUserId(Long userId);
    void deleteByPostId(Long postId);

    List<Comment> findByPostIdOrderByCreatedAtAsc(Long postId);

    void deleteById(Long id);
}