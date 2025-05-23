package org.attractor.microgram.service.impl;

import lombok.RequiredArgsConstructor;
import org.attractor.microgram.repository.SubscriptionRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubscriptionCountService {
    private final SubscriptionRepository subscriptionRepository;

    public int getSubscriptionCount(Long userId) {
        return subscriptionRepository.countByFollowerId(userId);
    }

    public int getSubscribersCount(Long userId) {
        return subscriptionRepository.countByFollowedId(userId);
    }
}
