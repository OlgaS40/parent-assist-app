package com.parentapp.schedule_activity;

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
@RequestMapping(value = "/api/scheduleActivities", produces = MediaType.APPLICATION_JSON_VALUE)
public class ScheduleActivityController {

    private final ScheduleActivityService scheduleActivityService;

    public ScheduleActivityController(final ScheduleActivityService scheduleActivityService) {
        this.scheduleActivityService = scheduleActivityService;
    }

    @GetMapping
    public ResponseEntity<List<ScheduleActivityDTO>> getAllScheduleActivities() {
        return ResponseEntity.ok(scheduleActivityService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleActivityDTO> getScheduleActivity(
            @PathVariable final String id) {
        return ResponseEntity.ok(scheduleActivityService.get(id));
    }

    @PostMapping("/notify")
    @CrossOrigin("*")
    public ResponseEntity<Void> notifyCreatedScheduleActivities() {
        scheduleActivityService.notifyCreatedActivities();
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Void> createScheduleActivity(
            @RequestBody @Valid final ScheduleActivityDTO scheduleActivityDTO,
            final BindingResult bindingResult) throws MethodArgumentNotValidException {
        if (!bindingResult.hasFieldErrors("id") && scheduleActivityDTO.getId() == null) {
            bindingResult.rejectValue("id", "NotNull");
        }
        if (!bindingResult.hasFieldErrors("id") && scheduleActivityService.idExists(scheduleActivityDTO.getId())) {
            bindingResult.rejectValue("id", "Exists.scheduleActivities.id");
        }
        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(new MethodParameter(
                    this.getClass().getDeclaredMethods()[0], -1), bindingResult);
        }
        scheduleActivityService.create(scheduleActivityDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateScheduleActivity(@PathVariable final String id,
                                                       @RequestBody @Valid final ScheduleActivityDTO scheduleActivityDTO) {
        scheduleActivityService.update(id, scheduleActivityDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteScheduleActivity(@PathVariable final String id) {
        scheduleActivityService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
