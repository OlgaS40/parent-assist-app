package com.parentapp.parent_assist.plan;

import com.parentapp.parent_assist.product.Product;
import com.parentapp.parent_assist.product.ProductRepository;
import com.parentapp.parent_assist.util.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class PlanService {

    private final PlanRepository planRepository;
    private final ProductRepository productRepository;

    public PlanService(final PlanRepository planRepository,
            final ProductRepository productRepository) {
        this.planRepository = planRepository;
        this.productRepository = productRepository;
    }

    public List<PlanDTO> findAll() {
        final List<Plan> plans = planRepository.findAll(Sort.by("id"));
        return plans.stream()
                .map((plan) -> mapToDTO(plan, new PlanDTO()))
                .collect(Collectors.toList());
    }

    public PlanDTO get(final String id) {
        return planRepository.findById(id)
                .map(plan -> mapToDTO(plan, new PlanDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public String create(final PlanDTO planDTO) {
        final Plan plan = new Plan();
        mapToEntity(planDTO, plan);
        plan.setId(planDTO.getId());
        return planRepository.save(plan).getId();
    }

    public void update(final String id, final PlanDTO planDTO) {
        final Plan plan = planRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(planDTO, plan);
        planRepository.save(plan);
    }

    public void delete(final String id) {
        planRepository.deleteById(id);
    }

    private PlanDTO mapToDTO(final Plan plan, final PlanDTO planDTO) {
        planDTO.setId(plan.getId());
        planDTO.setName(plan.getName());
        planDTO.setInterval(plan.getInterval());
        planDTO.setProductId(plan.getProductId() == null ? null : plan.getProductId().getId());
        return planDTO;
    }

    private Plan mapToEntity(final PlanDTO planDTO, final Plan plan) {
        plan.setName(planDTO.getName());
        plan.setInterval(planDTO.getInterval());
        final Product productId = planDTO.getProductId() == null ? null : productRepository.findById(planDTO.getProductId())
                .orElseThrow(() -> new NotFoundException("productId not found"));
        plan.setProductId(productId);
        return plan;
    }

    public boolean idExists(final String id) {
        return planRepository.existsByIdIgnoreCase(id);
    }

}
