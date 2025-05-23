package org.attractor.microgram.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.attractor.microgram.dto.CommentDto;
import org.attractor.microgram.model.Comment;
import org.attractor.microgram.model.Post;
import org.attractor.microgram.model.User;
import org.attractor.microgram.repository.CommentRepository;
import org.attractor.microgram.repository.PostRepository;
import org.attractor.microgram.service.CommentService;
import org.attractor.microgram.service.PostService;
import org.attractor.microgram.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostService postService;
    private final UserService userService;

    @Transactional
    @Override
    public void deleteCommentsByPostId(Long postId) {
        log.info("Deleting comments for post with id: {}", postId);
        commentRepository.deleteByPostId(postId);
        log.info("Comments for post with id: {} deleted successfully", postId);
    }

    @Transactional
    @Override
    public CommentDto createComment(Long postId, String username, String content) {
        log.info("Creating comment for post with id: {} by user: {}", postId, username);
        User user = userService.getUserByNameModel(username);
        Post post = postService.getPostByIdModel(postId);

        post.setCommentsCount(post.getCommentsCount() + 1);
        postService.save(post);
        Comment comment = Comment.builder()
                .post(post)
                .user(user)
                .text(content)
                .build();

        Comment savedComment = commentRepository.save(comment);
        log.info("Comment created with id: {} for post: {}", savedComment.getId(), postId);

        return mapToDto(savedComment);
    }

    @Transactional
    @Override
    public void deleteComment(Long commentId) {
        log.info("Deleting comment with id: {}", commentId);
        commentRepository.deleteById(commentId);
        log.info("Comment with id: {} deleted successfully", commentId);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(Long postId) {
        log.info("Fetching comments for post with id: {}", postId);
        return commentRepository.findByPostIdOrderByCreatedAtAsc(postId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private CommentDto mapToDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .postId(comment.getPost().getId())
                .username(comment.getUser().getUsername())
                .text(comment.getText())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}