package com.parentapp.plan_pricing;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/planPricing", produces = MediaType.APPLICATION_JSON_VALUE)
public class PlanPricingController {

    private final PlanPricingService planPricingService;

    public PlanPricingController(final PlanPricingService planPricingService) {
        this.planPricingService = planPricingService;
    }

    @GetMapping
    public ResponseEntity<List<PlanPricingDTO>> getAllPlanPricing() {
        return ResponseEntity.ok(planPricingService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanPricingDTO> getPlanPricing(@PathVariable final String id) {
        return ResponseEntity.ok(planPricingService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Void> createPlanPricing(
            @RequestBody @Valid final PlanPricingDTO planPricingDTO,
            final BindingResult bindingResult) throws MethodArgumentNotValidException {
        if (!bindingResult.hasFieldErrors("id") && planPricingDTO.getId() == null) {
            bindingResult.rejectValue("id", "NotNull");
        }
        if (!bindingResult.hasFieldErrors("id") && planPricingService.idExists(planPricingDTO.getId())) {
            bindingResult.rejectValue("id", "Exists.planPricing.id");
        }
        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(new MethodParameter(
                    this.getClass().getDeclaredMethods()[0], -1), bindingResult);
        }
        planPricingService.create(planPricingDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePlanPricing(@PathVariable final String id,
            @RequestBody @Valid final PlanPricingDTO planPricingDTO) {
        planPricingService.update(id, planPricingDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deletePlanPricing(@PathVariable final String id) {
        planPricingService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
