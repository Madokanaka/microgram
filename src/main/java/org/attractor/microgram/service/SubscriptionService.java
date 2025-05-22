package org.attractor.microgram.service;

public interface SubscriptionService {
    int getSubscribersCount(Long userId);

    int getSubscriptionCount(Long userId);
}
