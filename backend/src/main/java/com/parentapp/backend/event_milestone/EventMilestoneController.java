package com.parentapp.backend.event_milestone;

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
@RequestMapping(value = "/api/eventMilestones", produces = MediaType.APPLICATION_JSON_VALUE)
public class EventMilestoneController {

    private final EventMilestoneService eventMilestoneService;

    public EventMilestoneController(final EventMilestoneService eventMilestoneService) {
        this.eventMilestoneService = eventMilestoneService;
    }

    @GetMapping
    public ResponseEntity<List<EventMilestoneDTO>> getAllEventMilestones() {
        return ResponseEntity.ok(eventMilestoneService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventMilestoneDTO> getEventMilestone(@PathVariable final String id) {
        return ResponseEntity.ok(eventMilestoneService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Void> createEventMilestone(
            @RequestBody @Valid final EventMilestoneDTO eventMilestoneDTO,
            final BindingResult bindingResult) throws MethodArgumentNotValidException {
        if (!bindingResult.hasFieldErrors("id") && eventMilestoneDTO.getId() == null) {
            bindingResult.rejectValue("id", "NotNull");
        }
        if (!bindingResult.hasFieldErrors("id") && eventMilestoneService.idExists(eventMilestoneDTO.getId())) {
            bindingResult.rejectValue("id", "Exists.eventMilestone.id");
        }
        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(new MethodParameter(
                    this.getClass().getDeclaredMethods()[0], -1), bindingResult);
        }
        eventMilestoneService.create(eventMilestoneDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateEventMilestone(@PathVariable final String id,
            @RequestBody @Valid final EventMilestoneDTO eventMilestoneDTO) {
        eventMilestoneService.update(id, eventMilestoneDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteEventMilestone(@PathVariable final String id) {
        eventMilestoneService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
