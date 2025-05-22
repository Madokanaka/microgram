package org.attractor.microgram.service.impl;

import lombok.RequiredArgsConstructor;
import org.attractor.microgram.repository.SubscriptionRepository;
import org.attractor.microgram.service.SubscriptionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepository repository;

    @Override
    public int getSubscribersCount(Long userId) {
        return repository.countByFollowedId(userId);
    }

    @Override
    public int getSubscriptionCount(Long userId) {
        return repository.countByFollowerId(userId);
    }
}
