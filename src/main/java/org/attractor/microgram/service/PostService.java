package org.attractor.microgram.service;

import org.attractor.microgram.dto.PostDto;
import org.attractor.microgram.model.Post;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PostService {
    List<PostDto> getPostsByUserId(Long userId);

    @Transactional
    void createPost(String userName, PostDto postDto);

    PostDto getPostById(Long postId);

    Post getPostByIdModel(Long postId);

    @Transactional
    void deletePost(Long id);

    void save(Post post);

    List<PostDto> getFeedPosts(String username);
}
