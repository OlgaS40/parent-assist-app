package com.parentapp.backend.milestone;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/milestones", produces = MediaType.APPLICATION_JSON_VALUE)
public class MilestoneController {
    private final MilestoneService milestoneService;

    public MilestoneController(final MilestoneService milestoneService) {
        this.milestoneService = milestoneService;
    }

    @GetMapping
    public ResponseEntity<List<MilestoneDTO>> getAllMilestones() {
        return ResponseEntity.ok(milestoneService.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<MilestoneDTO> getMilestoneById(@PathVariable final String id) {
        return ResponseEntity.ok(milestoneService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Void> createMilestone(@RequestBody @Valid final MilestoneDTO milestoneDTO,
                                            final BindingResult bindingResult) throws MethodArgumentNotValidException {
        if (!bindingResult.hasFieldErrors("id") && milestoneDTO.getId() == null) {
            bindingResult.rejectValue("id", "NotNull");
        }
        if (!bindingResult.hasFieldErrors("id") && milestoneService.idExists(milestoneDTO.getId())) {
            bindingResult.rejectValue("id", "Exists.milestone.id");
        }
        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(new MethodParameter(
                    this.getClass().getDeclaredMethods()[0], -1), bindingResult);
        }
        milestoneService.create(milestoneDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateMilestone(@PathVariable final String id,
                                            @RequestBody @Valid final MilestoneDTO milestoneDTO) {
        milestoneService.update(id, milestoneDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteMilestone(@PathVariable final String id) {
        milestoneService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
