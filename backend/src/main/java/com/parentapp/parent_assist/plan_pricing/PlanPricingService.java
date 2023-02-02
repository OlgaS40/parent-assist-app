package com.parentapp.parent_assist.plan_pricing;

import com.parentapp.parent_assist.plan.Plan;
import com.parentapp.parent_assist.plan.PlanRepository;
import com.parentapp.parent_assist.util.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class PlanPricingService {

    private final PlanPricingRepository planPricingRepository;
    private final PlanRepository planRepository;

    public PlanPricingService(final PlanPricingRepository planPricingRepository,
            final PlanRepository planRepository) {
        this.planPricingRepository = planPricingRepository;
        this.planRepository = planRepository;
    }

    public List<PlanPricingDTO> findAll() {
        final List<PlanPricing> planPricings = planPricingRepository.findAll(Sort.by("id"));
        return planPricings.stream()
                .map((planPricing) -> mapToDTO(planPricing, new PlanPricingDTO()))
                .collect(Collectors.toList());
    }

    public PlanPricingDTO get(final String id) {
        return planPricingRepository.findById(id)
                .map(planPricing -> mapToDTO(planPricing, new PlanPricingDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public String create(final PlanPricingDTO planPricingDTO) {
        final PlanPricing planPricing = new PlanPricing();
        mapToEntity(planPricingDTO, planPricing);
        planPricing.setId(planPricingDTO.getId());
        return planPricingRepository.save(planPricing).getId();
    }

    public void update(final String id, final PlanPricingDTO planPricingDTO) {
        final PlanPricing planPricing = planPricingRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(planPricingDTO, planPricing);
        planPricingRepository.save(planPricing);
    }

    public void delete(final String id) {
        planPricingRepository.deleteById(id);
    }

    private PlanPricingDTO mapToDTO(final PlanPricing planPricing,
            final PlanPricingDTO planPricingDTO) {
        planPricingDTO.setId(planPricing.getId());
        planPricingDTO.setCurrency(planPricing.getCurrency());
        planPricingDTO.setPrice(planPricing.getPrice());
        planPricingDTO.setStartingAt(planPricing.getStartingAt());
        planPricingDTO.setEndingAt(planPricing.getEndingAt());
        planPricingDTO.setPlanId(planPricing.getPlanId() == null ? null : planPricing.getPlanId().getId());
        return planPricingDTO;
    }

    private PlanPricing mapToEntity(final PlanPricingDTO planPricingDTO,
            final PlanPricing planPricing) {
        planPricing.setCurrency(planPricingDTO.getCurrency());
        planPricing.setPrice(planPricingDTO.getPrice());
        planPricing.setStartingAt(planPricingDTO.getStartingAt());
        planPricing.setEndingAt(planPricingDTO.getEndingAt());
        final Plan planId = planPricingDTO.getPlanId() == null ? null : planRepository.findById(planPricingDTO.getPlanId())
                .orElseThrow(() -> new NotFoundException("planId not found"));
        planPricing.setPlanId(planId);
        return planPricing;
    }

    public boolean idExists(final String id) {
        return planPricingRepository.existsByIdIgnoreCase(id);
    }

}
