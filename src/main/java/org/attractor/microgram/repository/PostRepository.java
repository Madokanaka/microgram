package org.attractor.microgram.repository;

import org.attractor.microgram.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByUserIdOrderByCreatedAtDesc(Long userId);
    List<Post> findByUserIdInOrderByCreatedAtDesc(List<Long> userIds);
}