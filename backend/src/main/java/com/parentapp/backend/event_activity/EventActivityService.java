package com.parentapp.backend.event_activity;

import com.parentapp.backend.activity.Activity;
import com.parentapp.backend.activity.ActivityRepository;
import com.parentapp.backend.child.Child;
import com.parentapp.backend.child.ChildRepository;
import com.parentapp.backend.util.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class EventActivityService {

    private final EventActivityRepository eventActivityRepository;
    private final ActivityRepository activityRepository;
    private final ChildRepository childRepository;

    public EventActivityService(final EventActivityRepository eventActivityRepository,
            final ActivityRepository activityRepository,
            final ChildRepository childRepository) {
        this.eventActivityRepository = eventActivityRepository;
        this.activityRepository = activityRepository;
        this.childRepository = childRepository;
    }

    public List<EventActivityDTO> findAll() {
        final List<EventActivity> eventActivities = eventActivityRepository.findAll(Sort.by("id"));
        return eventActivities.stream()
                .map((eventActivity) -> mapToDTO(eventActivity, new EventActivityDTO()))
                .collect(Collectors.toList());
    }

    public EventActivityDTO get(final String id) {
        return eventActivityRepository.findById(id)
                .map(eventActivity -> mapToDTO(eventActivity, new EventActivityDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public String create(final EventActivityDTO eventActivityDTO) {
        final EventActivity eventActivity = new EventActivity();
        mapToEntity(eventActivityDTO, eventActivity);
        eventActivity.setId(eventActivityDTO.getId());
        return eventActivityRepository.save(eventActivity).getId();
    }

    public void update(final String id, final EventActivityDTO eventActivityDTO) {
        final EventActivity eventActivity = eventActivityRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(eventActivityDTO, eventActivity);
        eventActivityRepository.save(eventActivity);
    }

    public void delete(final String id) {
        eventActivityRepository.deleteById(id);
    }

    private EventActivityDTO mapToDTO(final EventActivity eventActivity,
            final EventActivityDTO eventActivityDTO) {
        eventActivityDTO.setId(eventActivity.getId());
        eventActivityDTO.setDate(eventActivity.getDate());
        eventActivityDTO.setDifficulty(eventActivity.getDifficulty());
        eventActivityDTO.setInterest(eventActivity.getInterest());
        eventActivityDTO.setFeedback(eventActivity.getFeedback());
        eventActivityDTO.setActivityId(eventActivity.getActivity() == null ? null : eventActivity.getActivity().getId());
        eventActivityDTO.setChildId(eventActivity.getChild() == null ? null : eventActivity.getChild().getId());
        return eventActivityDTO;
    }

    private EventActivity mapToEntity(final EventActivityDTO eventActivityDTO,
            final EventActivity eventActivity) {
        eventActivity.setDate(eventActivityDTO.getDate());
        eventActivity.setDifficulty(eventActivityDTO.getDifficulty());
        eventActivity.setInterest(eventActivityDTO.getInterest());
        eventActivity.setFeedback(eventActivityDTO.getFeedback());
        final Activity activityId = eventActivityDTO.getActivityId() == null ? null : activityRepository.findById(eventActivityDTO.getActivityId())
                .orElseThrow(() -> new NotFoundException("activityId not found"));
        eventActivity.setActivity(activityId);
        final Child childId = eventActivityDTO.getChildId() == null ? null : childRepository.findById(eventActivityDTO.getChildId())
                .orElseThrow(() -> new NotFoundException("childId not found"));
        eventActivity.setChild(childId);
        return eventActivity;
    }

    public boolean idExists(final String id) {
        return eventActivityRepository.existsByIdIgnoreCase(id);
    }

}
