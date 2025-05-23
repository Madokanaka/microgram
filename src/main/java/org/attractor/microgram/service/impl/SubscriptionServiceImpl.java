package org.attractor.microgram.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.attractor.microgram.model.Subscription;
import org.attractor.microgram.model.User;
import org.attractor.microgram.repository.SubscriptionRepository;
import org.attractor.microgram.service.SubscriptionService;
import org.attractor.microgram.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final UserService userService;

    @Override
    public int getSubscribersCount(Long userId) {
        return subscriptionRepository.countByFollowedId(userId);
    }

    @Override
    public int getSubscriptionCount(Long userId) {
        return subscriptionRepository.countByFollowerId(userId);
    }

    @Transactional
    @Override
    public void subscribe(String followerUsername, String followedUsername) {
        log.info("User {} subscribing to user {}", followerUsername, followedUsername);
        User follower = userService.getUserByNameModel(followerUsername);
        User followed = userService.getUserByNameModel(followedUsername);

        if (!subscriptionRepository.existsByFollowerIdAndFollowedId(follower.getId(), followed.getId())) {
            Subscription subscription = Subscription.builder()
                    .follower(follower)
                    .followed(followed)
                    .build();
            subscriptionRepository.save(subscription);

            log.info("User {} subscribed to user {}", followerUsername, followedUsername);
        }
    }

    @Transactional
    @Override
    public void unsubscribe(String followerUsername, String followedUsername) {
        log.info("User {} unsubscribing from user {}", followerUsername, followedUsername);
        User follower = userService.getUserByNameModel(followerUsername);
        User followed = userService.getUserByNameModel(followedUsername);

        if (subscriptionRepository.existsByFollowerIdAndFollowedId(follower.getId(), followed.getId())) {
            subscriptionRepository.deleteByFollowerIdAndFollowedId(follower.getId(), followed.getId());
            log.info("User {} unsubscribed from user {}", followerUsername, followedUsername);
        }
    }

    @Override
    public boolean isSubscribed(String followerUsername, String followedUsername) {
        log.info("Checking if user {} is subscribed to user {}", followerUsername, followedUsername);
        User follower = userService.getUserByNameModel(followerUsername);
        User followed = userService.getUserByNameModel(followedUsername);
        return subscriptionRepository.existsByFollowerIdAndFollowedId(follower.getId(), followed.getId());
    }

    @Override
    public List<Long> getFollowedUserIds(String followerUsername) {
        log.info("Fetching followed user IDs for user: {}", followerUsername);
        User follower = userService.getUserByNameModel(followerUsername);
        return subscriptionRepository.findByFollowerId(follower.getId()).stream()
                .map(subscription -> subscription.getFollowed().getId())
                .collect(Collectors.toList());
    }
}
