package com.parentapp.parent_assist.purchase;

import com.parentapp.parent_assist.subscription.Subscription;
import com.parentapp.parent_assist.subscription.SubscriptionController;
import com.parentapp.parent_assist.subscription.SubscriptionRepository;
import com.parentapp.parent_assist.util.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final SubscriptionRepository subscriptionRepository;

    public PurchaseService(final PurchaseRepository purchaseRepository,
            final SubscriptionRepository subscriptionRepository) {
        this.purchaseRepository = purchaseRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    public List<PurchaseDTO> findAll() {
        final List<Purchase> purchases = purchaseRepository.findAll(Sort.by("id"));
        return purchases.stream()
                .map((purchase) -> mapToDTO(purchase, new PurchaseDTO()))
                .collect(Collectors.toList());
    }

    public PurchaseDTO get(final String id) {
        return purchaseRepository.findById(id)
                .map(purchase -> mapToDTO(purchase, new PurchaseDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public String create(final PurchaseDTO purchaseDTO) {
        final Purchase purchase = new Purchase();
        mapToEntity(purchaseDTO, purchase);
        purchase.setId(purchaseDTO.getId());
        return purchaseRepository.save(purchase).getId();
    }

    public void update(final String id, final PurchaseDTO purchaseDTO) {
        final Purchase purchase = purchaseRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(purchaseDTO, purchase);
        purchaseRepository.save(purchase);
    }

    public void delete(final String id) {
        purchaseRepository.deleteById(id);
    }

    private PurchaseDTO mapToDTO(final Purchase purchase, final PurchaseDTO purchaseDTO) {
        purchaseDTO.setId(purchase.getId());
        purchaseDTO.setDate(purchase.getDate());
        purchaseDTO.setPaymentMethod(purchase.getPaymentMethod());
        purchaseDTO.setAmount(purchase.getAmount());
        purchaseDTO.setSubscriptionId(purchase.getSubscriptionId() == null ? null : purchase.getSubscriptionId().getId());
        return purchaseDTO;
    }

    private Purchase mapToEntity(final PurchaseDTO purchaseDTO, final Purchase purchase) {
        purchase.setDate(purchaseDTO.getDate());
        purchase.setPaymentMethod(purchaseDTO.getPaymentMethod());
        purchase.setAmount(purchaseDTO.getAmount());
        final Subscription subscriptionId = purchaseDTO.getSubscriptionId() == null ? null : subscriptionRepository.findById(purchaseDTO.getSubscriptionId())
                .orElseThrow(() -> new NotFoundException("subscriptionId not found"));
        purchase.setSubscriptionId(subscriptionId);
        return purchase;
    }

    public boolean idExists(final String id) {
        return purchaseRepository.existsByIdIgnoreCase(id);
    }

}
