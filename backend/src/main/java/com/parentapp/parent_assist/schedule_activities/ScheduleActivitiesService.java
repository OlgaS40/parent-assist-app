package com.parentapp.parent_assist.schedule_activities;

import com.parentapp.parent_assist.activities.Activities;
import com.parentapp.parent_assist.activities.ActivitiesRepository;
import com.parentapp.parent_assist.util.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ScheduleActivitiesService {

    private final ScheduleActivitiesRepository scheduleActivitiesRepository;
    private final ActivitiesRepository activitiesRepository;

    public ScheduleActivitiesService(
            final ScheduleActivitiesRepository scheduleActivitiesRepository,
            final ActivitiesRepository activitiesRepository) {
        this.scheduleActivitiesRepository = scheduleActivitiesRepository;
        this.activitiesRepository = activitiesRepository;
    }

    public List<ScheduleActivitiesDTO> findAll() {
        final List<ScheduleActivities> scheduleActivitiess = scheduleActivitiesRepository.findAll(Sort.by("id"));
        return scheduleActivitiess.stream()
                .map((scheduleActivities) -> mapToDTO(scheduleActivities, new ScheduleActivitiesDTO()))
                .collect(Collectors.toList());
    }

    public ScheduleActivitiesDTO get(final String id) {
        return scheduleActivitiesRepository.findById(id)
                .map(scheduleActivities -> mapToDTO(scheduleActivities, new ScheduleActivitiesDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public String create(final ScheduleActivitiesDTO scheduleActivitiesDTO) {
        final ScheduleActivities scheduleActivities = new ScheduleActivities();
        mapToEntity(scheduleActivitiesDTO, scheduleActivities);
        scheduleActivities.setId(scheduleActivitiesDTO.getId());
        return scheduleActivitiesRepository.save(scheduleActivities).getId();
    }

    public void update(final String id, final ScheduleActivitiesDTO scheduleActivitiesDTO) {
        final ScheduleActivities scheduleActivities = scheduleActivitiesRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(scheduleActivitiesDTO, scheduleActivities);
        scheduleActivitiesRepository.save(scheduleActivities);
    }

    public void delete(final String id) {
        scheduleActivitiesRepository.deleteById(id);
    }

    private ScheduleActivitiesDTO mapToDTO(final ScheduleActivities scheduleActivities,
            final ScheduleActivitiesDTO scheduleActivitiesDTO) {
        scheduleActivitiesDTO.setId(scheduleActivities.getId());
        scheduleActivitiesDTO.setPeriodFrom(scheduleActivities.getPeriodFrom());
        scheduleActivitiesDTO.setPeriodTo(scheduleActivities.getPeriodTo());
        scheduleActivitiesDTO.setActivityId(scheduleActivities.getActivityId() == null ? null : scheduleActivities.getActivityId().getId());
        return scheduleActivitiesDTO;
    }

    private ScheduleActivities mapToEntity(final ScheduleActivitiesDTO scheduleActivitiesDTO,
            final ScheduleActivities scheduleActivities) {
        scheduleActivities.setPeriodFrom(scheduleActivitiesDTO.getPeriodFrom());
        scheduleActivities.setPeriodTo(scheduleActivitiesDTO.getPeriodTo());
        final Activities activityId = scheduleActivitiesDTO.getActivityId() == null ? null : activitiesRepository.findById(scheduleActivitiesDTO.getActivityId())
                .orElseThrow(() -> new NotFoundException("activityId not found"));
        scheduleActivities.setActivityId(activityId);
        return scheduleActivities;
    }

    public boolean idExists(final String id) {
        return scheduleActivitiesRepository.existsByIdIgnoreCase(id);
    }

}
