package org.attractor.microgram.service;

import java.util.List;

public interface SubscriptionService {
    int getSubscribersCount(Long userId);

    int getSubscriptionCount(Long userId);

    void subscribe(String followerUsername, String followedUsername);
    void unsubscribe(String followerUsername, String followedUsername);
    boolean isSubscribed(String followerUsername, String followedUsername);
    List<Long> getFollowedUserIds(String followerUsername);
}
