package org.attractor.microgram.service;

import org.attractor.microgram.dto.CommentDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CommentService {
    void deleteCommentsByPostId(Long postId);

    @Transactional
    CommentDto createComment(Long postId, String username, String content);

    @Transactional
    void deleteComment(Long commentId);

    List<CommentDto> getCommentsByPostId(Long postId);
}