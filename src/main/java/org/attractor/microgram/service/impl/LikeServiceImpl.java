package org.attractor.microgram.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.attractor.microgram.model.Like;
import org.attractor.microgram.model.Post;
import org.attractor.microgram.model.User;
import org.attractor.microgram.repository.LikeRepository;
import org.attractor.microgram.repository.PostRepository;
import org.attractor.microgram.repository.UserRepository;
import org.attractor.microgram.service.LikeService;
import org.attractor.microgram.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;

    private final UserService userService;
    private final PostServiceImpl postService;

    @Override
    public boolean hasLiked(Long postId, String username) {
        log.info("Checking if user {} has liked post with id: {}", username, postId);
        User user = userService.getUserByNameModel(username);
        return likeRepository.findByPostIdAndUserId(postId, user.getId()).isPresent();
    }

    @Transactional
    @Override
    public void toggleLike(Long postId, String username) {
        log.info("Toggling like for post with id: {} by user: {}", postId, username);
        User user = userService.getUserByNameModel(username);
        Post post = postService.getPostByIdModel(postId);

        Optional<Like> existingLike = likeRepository.findByPostIdAndUserId(postId, user.getId());
        if (existingLike.isPresent()) {
            likeRepository.delete(existingLike.get());
            post.setLikesCount(post.getLikesCount() - 1);
            log.info("Like removed for post with id: {} by user: {}", postId, username);
        } else {
            Like like = Like.builder()
                    .post(post)
                    .user(user)
                    .build();
            likeRepository.save(like);
            post.setLikesCount(post.getLikesCount() + 1);
            log.info("Like added for post with id: {} by user: {}", postId, username);
        }
        postService.save(post);
    }

    @Transactional
    @Override
    public void deleteLikesByPostId(Long postId) {
        log.info("Deleting likes for post with id: {}", postId);
        likeRepository.deleteByPostId(postId);
        log.info("Likes for post with id: {} deleted successfully", postId);
    }
}