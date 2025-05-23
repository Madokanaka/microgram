package org.attractor.microgram.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.attractor.microgram.repository.CommentRepository;
import org.attractor.microgram.service.CommentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    @Override
    public void deleteCommentsByPostId(Long postId) {
        log.info("Deleting comments for post with id: {}", postId);
        commentRepository.deleteByPostId(postId);
        log.info("Comments for post with id: {} deleted successfully", postId);
    }
}