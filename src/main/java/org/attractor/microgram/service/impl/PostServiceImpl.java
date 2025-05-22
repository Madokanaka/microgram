package org.attractor.microgram.service.impl;

import lombok.RequiredArgsConstructor;
import org.attractor.microgram.dto.PostDto;
import org.attractor.microgram.model.Post;
import org.attractor.microgram.repository.PostRepository;
import org.attractor.microgram.service.PostService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

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
}
