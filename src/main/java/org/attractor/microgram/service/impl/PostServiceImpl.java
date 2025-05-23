package org.attractor.microgram.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.attractor.microgram.dto.PostDto;
import org.attractor.microgram.model.Post;
import org.attractor.microgram.model.User;
import org.attractor.microgram.repository.PostRepository;
import org.attractor.microgram.service.PostService;
import org.attractor.microgram.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    @Override
    public List<PostDto> getPostsByUserId(Long userId) {
        return postRepository.findAllByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private PostDto mapToDto(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .userId(post.getUser().getId())
                .username(post.getUser().getUsername())
                .imageUrl(post.getImageUrl())
                .description(post.getDescription())
                .createdAt(post.getCreatedAt())
                .likesCount(post.getLikesCount())
                .commentsCount(post.getCommentsCount())
                .build();
    }

    @Transactional
    @Override
    public void createPost(String userName, PostDto postDto) {
        log.info("Creating post for user: {}", userName);
        User user = userService.getUserByNameModel(userName);

        Post post = Post.builder()
                .user(user)
                .imageUrl(postDto.getImageUrl())
                .description(postDto.getDescription())
                .likesCount(0L)
                .commentsCount(0L)
                .build();

        postRepository.save(post);
        log.info("Post created for user: {}", userName);
    }
}
