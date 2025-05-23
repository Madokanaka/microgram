package org.attractor.microgram.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.attractor.microgram.dto.PostDto;
import org.attractor.microgram.exception.ResourceNotFoundException;
import org.attractor.microgram.model.Post;
import org.attractor.microgram.model.User;
import org.attractor.microgram.repository.PostRepository;
import org.attractor.microgram.service.PostService;
import org.attractor.microgram.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.attractor.microgram.service.SubscriptionService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserService userService;
    private final SubscriptionService subscriptionService;

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

    @Override
    public PostDto getPostById(Long postId) {
        log.info("Getting post by id: {}", postId);
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found: " + postId));
        log.info("Post found: {}", post);
        return mapToDto(post);
    }

    @Override
    public Post getPostByIdModel(Long postId) {
        log.info("Getting post by id: {}", postId);
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found: " + postId));
        log.info("Post found: {}", post);
        return post;
    }

    @Transactional
    @Override
    public void deletePost(Long id) {
        log.info("Deleting post with id: {}", id);
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        postRepository.delete(post);
        log.info("Post with id: {} deleted successfully", id);
    }

    @Override
    public void save(Post post) {
        postRepository.save(post);
    }

    @Override
    public List<PostDto> getFeedPosts(String username) {
        log.info("Fetching feed posts for user: {}", username);
        List<Long> followedUserIds = subscriptionService.getFollowedUserIds(username);

        return postRepository.findByUserIdInOrderByCreatedAtDesc(followedUserIds).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
}
