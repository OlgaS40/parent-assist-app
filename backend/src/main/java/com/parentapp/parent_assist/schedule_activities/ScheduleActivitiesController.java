package com.parentapp.parent_assist.schedule_activities;

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
@RequestMapping(value = "/api/scheduleActivitiess", produces = MediaType.APPLICATION_JSON_VALUE)
public class ScheduleActivitiesController {

    private final ScheduleActivitiesService scheduleActivitiesService;

    public ScheduleActivitiesController(final ScheduleActivitiesService scheduleActivitiesService) {
        this.scheduleActivitiesService = scheduleActivitiesService;
    }

    @GetMapping
    public ResponseEntity<List<ScheduleActivitiesDTO>> getAllScheduleActivitiess() {
        return ResponseEntity.ok(scheduleActivitiesService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleActivitiesDTO> getScheduleActivities(
            @PathVariable final String id) {
        return ResponseEntity.ok(scheduleActivitiesService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Void> createScheduleActivities(
            @RequestBody @Valid final ScheduleActivitiesDTO scheduleActivitiesDTO,
            final BindingResult bindingResult) throws MethodArgumentNotValidException {
        if (!bindingResult.hasFieldErrors("id") && scheduleActivitiesDTO.getId() == null) {
            bindingResult.rejectValue("id", "NotNull");
        }
        if (!bindingResult.hasFieldErrors("id") && scheduleActivitiesService.idExists(scheduleActivitiesDTO.getId())) {
            bindingResult.rejectValue("id", "Exists.scheduleActivities.id");
        }
        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(new MethodParameter(
                    this.getClass().getDeclaredMethods()[0], -1), bindingResult);
        }
        scheduleActivitiesService.create(scheduleActivitiesDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateScheduleActivities(@PathVariable final String id,
            @RequestBody @Valid final ScheduleActivitiesDTO scheduleActivitiesDTO) {
        scheduleActivitiesService.update(id, scheduleActivitiesDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteScheduleActivities(@PathVariable final String id) {
        scheduleActivitiesService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
