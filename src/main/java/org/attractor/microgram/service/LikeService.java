package org.attractor.microgram.service;

public interface LikeService {
    boolean hasLiked(Long postId, String username);
    void toggleLike(Long postId, String username);
    void deleteLikesByPostId(Long postId);
}