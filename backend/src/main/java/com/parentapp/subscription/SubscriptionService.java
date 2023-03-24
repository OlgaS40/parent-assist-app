package com.parentapp.subscription;

import java.util.List;

public interface SubscriptionService {
    List<SubscriptionDTO> findAll();
    SubscriptionDTO get(final String id);
    String create(final SubscriptionDTO subscriptionDTO);
    void update(final String id, final SubscriptionDTO subscriptionDTO);
    void delete(final String id);
    boolean idExists(final String id);
}
