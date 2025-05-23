package org.attractor.microgram.service;

import org.attractor.microgram.dto.PostDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PostService {
    List<PostDto> getPostsByUserId(Long userId);

    @Transactional
    void createPost(String userName, PostDto postDto);

    PostDto getPostById(Long postId);

    @Transactional
    void deletePost(Long id);
}
