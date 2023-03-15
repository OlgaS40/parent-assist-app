package com.parentapp.subscription;

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
@RequestMapping(value = "/api/subscriptions", produces = MediaType.APPLICATION_JSON_VALUE)
public class SubscriptionController {

    private final SubscriptionServiceImpl subscriptionService;

    public SubscriptionController(final SubscriptionServiceImpl subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @GetMapping
    public ResponseEntity<List<SubscriptionDTO>> getAllSubscriptions() {
        return ResponseEntity.ok(subscriptionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionDTO> getSubscription(@PathVariable final String id) {
        return ResponseEntity.ok(subscriptionService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Void> createSubscription(
            @RequestBody @Valid final SubscriptionDTO subscriptionDTO,
            final BindingResult bindingResult) throws MethodArgumentNotValidException {
        if (!bindingResult.hasFieldErrors("id") && subscriptionDTO.getId() == null) {
            bindingResult.rejectValue("id", "NotNull");
        }
        if (!bindingResult.hasFieldErrors("id") && subscriptionService.idExists(subscriptionDTO.getId())) {
            bindingResult.rejectValue("id", "Exists.subscription.id");
        }
        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(new MethodParameter(
                    this.getClass().getDeclaredMethods()[0], -1), bindingResult);
        }
        subscriptionService.create(subscriptionDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateSubscription(@PathVariable final String id,
            @RequestBody @Valid final SubscriptionDTO subscriptionDTO) {
        subscriptionService.update(id, subscriptionDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteSubscription(@PathVariable final String id) {
        subscriptionService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
