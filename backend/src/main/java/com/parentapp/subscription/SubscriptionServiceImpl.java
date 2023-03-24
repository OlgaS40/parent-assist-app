package com.parentapp.subscription;

import com.parentapp.child.Child;
import com.parentapp.child.ChildRepository;
import com.parentapp.util.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final ChildRepository childRepository;

    public SubscriptionServiceImpl(final SubscriptionRepository subscriptionRepository,
                                   final ChildRepository childRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.childRepository = childRepository;
    }

    public List<SubscriptionDTO> findAll() {
        final List<Subscription> subscriptions = subscriptionRepository.findAll();
        return subscriptions.stream()
                .map((subscription) -> mapToDTO(subscription, new SubscriptionDTO()))
                .collect(Collectors.toList());
    }

    public SubscriptionDTO get(final String id) {
        return subscriptionRepository.findById(id)
                .map(subscription -> mapToDTO(subscription, new SubscriptionDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public String create(final SubscriptionDTO subscriptionDTO) {
        final Subscription subscription = new Subscription();
        mapToEntity(subscriptionDTO, subscription);
        subscription.setId(subscriptionDTO.getId());
        return subscriptionRepository.save(subscription).getId();
    }

    public void update(final String id, final SubscriptionDTO subscriptionDTO) {
        final Subscription subscription = subscriptionRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(subscriptionDTO, subscription);
        subscriptionRepository.save(subscription);
    }

    public void delete(final String id) {
        subscriptionRepository.deleteById(id);
    }

    private SubscriptionDTO mapToDTO(final Subscription subscription,
                                     final SubscriptionDTO subscriptionDTO) {
        subscriptionDTO.setId(subscription.getId());
        subscriptionDTO.setStart(subscription.getStart());
        subscriptionDTO.setEnd(subscription.getEnd());
        subscriptionDTO.setChildId(subscription.getChild() == null ? null : subscription.getChild().getId());
        return subscriptionDTO;
    }

    private Subscription mapToEntity(final SubscriptionDTO subscriptionDTO,
                                     final Subscription subscription) {
        subscription.setStart(subscriptionDTO.getStart());
        subscription.setEnd(subscriptionDTO.getEnd());
        final Child childId = subscriptionDTO.getChildId() == null ? null : childRepository.findById(subscriptionDTO.getChildId())
                .orElseThrow(() -> new NotFoundException("childId not found"));
        subscription.setChild(childId);
        return subscription;
    }

    public boolean idExists(final String id) {
        return subscriptionRepository.existsByIdIgnoreCase(id);
    }

}
