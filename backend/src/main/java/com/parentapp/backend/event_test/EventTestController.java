package com.parentapp.backend.event_test;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/eventTests", produces = MediaType.APPLICATION_JSON_VALUE)
public class EventTestController {

    private final EventTestService eventTestService;

    public EventTestController(final EventTestService eventTestService) {
        this.eventTestService = eventTestService;
    }

    @GetMapping
    public ResponseEntity<List<EventTestDTO>> getAllEventTests() {
        return ResponseEntity.ok(eventTestService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventTestDTO> getEventTest(@PathVariable final String id) {
        return ResponseEntity.ok(eventTestService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<String> createEventTest(
            @RequestBody @Valid final EventTestDTO eventTestDTO) {
        return new ResponseEntity<>(eventTestService.create(eventTestDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateEventTest(@PathVariable final String id,
            @RequestBody @Valid final EventTestDTO eventTestDTO) {
        eventTestService.update(id, eventTestDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteEventTest(@PathVariable final String id) {
        eventTestService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
