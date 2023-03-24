package com.parentapp.cashin;

import com.parentapp.subscription.Subscription;
import com.parentapp.subscription.SubscriptionRepository;
import com.parentapp.util.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class CashInService {

    private final CashInRepository cashInRepository;
    private final SubscriptionRepository subscriptionRepository;

    public CashInService(final CashInRepository cashInRepository,
                         final SubscriptionRepository subscriptionRepository) {
        this.cashInRepository = cashInRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    public List<CashInDTO> findAll() {
        final List<CashIn> cashIns = cashInRepository.findAll(Sort.by("id"));
        return cashIns.stream()
                .map((cashIn) -> mapToDTO(cashIn, new CashInDTO()))
                .collect(Collectors.toList());
    }

    public CashInDTO get(final String id) {
        return cashInRepository.findById(id)
                .map(cashIn -> mapToDTO(cashIn, new CashInDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public String create(final CashInDTO cashInDTO) {
        final CashIn cashIn = new CashIn();
        mapToEntity(cashInDTO, cashIn);
        cashIn.setId(cashInDTO.getId());
        return cashInRepository.save(cashIn).getId();
    }

    public void update(final String id, final CashInDTO cashInDTO) {
        final CashIn cashIn = cashInRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(cashInDTO, cashIn);
        cashInRepository.save(cashIn);
    }

    public void delete(final String id) {
        cashInRepository.deleteById(id);
    }

    private CashInDTO mapToDTO(final CashIn cashIn, final CashInDTO cashInDTO) {
        cashInDTO.setId(cashIn.getId());
        cashInDTO.setDate(cashIn.getDate());
        cashInDTO.setPaymentMethod(cashIn.getPaymentMethod());
        cashInDTO.setAmount(cashIn.getAmount());
        cashInDTO.setSubscriptionId(cashIn.getSubscription() == null ? null : cashIn.getSubscription().getId());
        return cashInDTO;
    }

    private CashIn mapToEntity(final CashInDTO cashInDTO, final CashIn cashIn) {
        cashIn.setDate(cashInDTO.getDate());
        cashIn.setPaymentMethod(cashInDTO.getPaymentMethod());
        cashIn.setAmount(cashInDTO.getAmount());
        final Subscription subscriptionId = cashInDTO.getSubscriptionId() == null ? null : subscriptionRepository.findById(cashInDTO.getSubscriptionId())
                .orElseThrow(() -> new NotFoundException("subscriptionId not found"));
        cashIn.setSubscription(subscriptionId);
        return cashIn;
    }

    public boolean idExists(final String id) {
        return cashInRepository.existsByIdIgnoreCase(id);
    }

}
