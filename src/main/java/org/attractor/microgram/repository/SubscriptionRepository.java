package org.attractor.microgram.repository;

import org.attractor.microgram.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    Optional<Subscription> findByFollowerIdAndFollowedId(Long followerId, Long followedId);
    int countByFollowedId(Long followedId);
    int countByFollowerId(Long followerId);
    boolean existsByFollowerIdAndFollowedId(Long followerId, Long followedId);
    void deleteByFollowerIdAndFollowedId(Long followerId, Long followedId);
    List<Subscription> findByFollowerId(Long followerId);
}