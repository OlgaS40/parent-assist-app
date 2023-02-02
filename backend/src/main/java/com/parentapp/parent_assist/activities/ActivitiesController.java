package com.parentapp.parent_assist.activities;

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
@RequestMapping(value = "/api/activitiess", produces = MediaType.APPLICATION_JSON_VALUE)
public class ActivitiesController {

    private final ActivitiesService activitiesService;

    public ActivitiesController(final ActivitiesService activitiesService) {
        this.activitiesService = activitiesService;
    }

    @GetMapping
    public ResponseEntity<List<ActivitiesDTO>> getAllActivitiess() {
        return ResponseEntity.ok(activitiesService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActivitiesDTO> getActivities(@PathVariable final String id) {
        return ResponseEntity.ok(activitiesService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Void> createActivities(
            @RequestBody @Valid final ActivitiesDTO activitiesDTO,
            final BindingResult bindingResult) throws MethodArgumentNotValidException {
        if (!bindingResult.hasFieldErrors("id") && activitiesDTO.getId() == null) {
            bindingResult.rejectValue("id", "NotNull");
        }
        if (!bindingResult.hasFieldErrors("id") && activitiesService.idExists(activitiesDTO.getId())) {
            bindingResult.rejectValue("id", "Exists.activities.id");
        }
        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(new MethodParameter(
                    this.getClass().getDeclaredMethods()[0], -1), bindingResult);
        }
        activitiesService.create(activitiesDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateActivities(@PathVariable final String id,
            @RequestBody @Valid final ActivitiesDTO activitiesDTO) {
        activitiesService.update(id, activitiesDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteActivities(@PathVariable final String id) {
        activitiesService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
