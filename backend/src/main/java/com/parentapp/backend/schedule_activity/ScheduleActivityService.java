package com.parentapp.backend.schedule_activity;

import com.parentapp.backend.activity.Activity;
import com.parentapp.backend.activity.ActivityRepository;
import com.parentapp.backend.util.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ScheduleActivityService {

    private final ScheduleActivityRepository scheduleActivityRepository;
    private final ActivityRepository activityRepository;

    public ScheduleActivityService(
            final ScheduleActivityRepository scheduleActivityRepository,
            final ActivityRepository activityRepository) {
        this.scheduleActivityRepository = scheduleActivityRepository;
        this.activityRepository = activityRepository;
    }

    public List<ScheduleActivityDTO> findAll() {
        final List<ScheduleActivity> scheduleActivities = scheduleActivityRepository.findAll(Sort.by("id"));
        return scheduleActivities.stream()
                .map((scheduleActivity) -> mapToDTO(scheduleActivity, new ScheduleActivityDTO()))
                .collect(Collectors.toList());
    }

    public ScheduleActivityDTO get(final String id) {
        return scheduleActivityRepository.findById(id)
                .map(scheduleActivity -> mapToDTO(scheduleActivity, new ScheduleActivityDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public String create(final ScheduleActivityDTO scheduleActivityDTO) {
        final ScheduleActivity scheduleActivity = new ScheduleActivity();
        mapToEntity(scheduleActivityDTO, scheduleActivity);
        scheduleActivity.setId(scheduleActivityDTO.getId());
        return scheduleActivityRepository.save(scheduleActivity).getId();
    }

    public void update(final String id, final ScheduleActivityDTO scheduleActivityDTO) {
        final ScheduleActivity scheduleActivity = scheduleActivityRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(scheduleActivityDTO, scheduleActivity);
        scheduleActivityRepository.save(scheduleActivity);
    }

    public void delete(final String id) {
        scheduleActivityRepository.deleteById(id);
    }

    private ScheduleActivityDTO mapToDTO(final ScheduleActivity scheduleActivity,
                                         final ScheduleActivityDTO scheduleActivityDTO) {
        scheduleActivityDTO.setId(scheduleActivity.getId());
        scheduleActivityDTO.setPeriodFrom(scheduleActivity.getPeriodFrom());
        scheduleActivityDTO.setPeriodTo(scheduleActivity.getPeriodTo());
        scheduleActivityDTO.setActivityId(scheduleActivity.getActivity() == null ? null : scheduleActivity.getActivity().getId());
        return scheduleActivityDTO;
    }

    private ScheduleActivity mapToEntity(final ScheduleActivityDTO scheduleActivityDTO,
                                         final ScheduleActivity scheduleActivity) {
        scheduleActivity.setPeriodFrom(scheduleActivityDTO.getPeriodFrom());
        scheduleActivity.setPeriodTo(scheduleActivityDTO.getPeriodTo());
        final Activity activityId = scheduleActivityDTO.getActivityId() == null ? null : activityRepository.findById(scheduleActivityDTO.getActivityId())
                .orElseThrow(() -> new NotFoundException("activityId not found"));
        scheduleActivity.setActivity(activityId);
        return scheduleActivity;
    }

    public boolean idExists(final String id) {
        return scheduleActivityRepository.existsByIdIgnoreCase(id);
    }

}
