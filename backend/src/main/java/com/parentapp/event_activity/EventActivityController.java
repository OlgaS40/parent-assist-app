package com.parentapp.event_activity;

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
@RequestMapping(value = "/api/eventActivities", produces = MediaType.APPLICATION_JSON_VALUE)
public class EventActivityController {

    private final EventActivityService eventActivityService;

    public EventActivityController(final EventActivityService eventActivityService) {
        this.eventActivityService = eventActivityService;
    }

    @GetMapping
    public ResponseEntity<List<EventActivityDTO>> getAllEventActivities() {
        return ResponseEntity.ok(eventActivityService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventActivityDTO> getEventActivity(@PathVariable final String id) {
        return ResponseEntity.ok(eventActivityService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Void> createEventActivity(
            @RequestBody @Valid final EventActivityDTO eventActivityDTO,
            final BindingResult bindingResult) throws MethodArgumentNotValidException {
        if (!bindingResult.hasFieldErrors("id") && eventActivityDTO.getId() == null) {
            bindingResult.rejectValue("id", "NotNull");
        }
        if (!bindingResult.hasFieldErrors("id") && eventActivityService.idExists(eventActivityDTO.getId())) {
            bindingResult.rejectValue("id", "Exists.eventActivity.id");
        }
        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(new MethodParameter(
                    this.getClass().getDeclaredMethods()[0], -1), bindingResult);
        }
        eventActivityService.create(eventActivityDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateEventActivity(@PathVariable final String id,
            @RequestBody @Valid final EventActivityDTO eventActivityDTO) {
        eventActivityService.update(id, eventActivityDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteEventActivity(@PathVariable final String id) {
        eventActivityService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
