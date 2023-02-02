package com.parentapp.parent_assist.event_activity;

import com.parentapp.parent_assist.activities.Activities;
import com.parentapp.parent_assist.activities.ActivitiesRepository;
import com.parentapp.parent_assist.children.Children;
import com.parentapp.parent_assist.children.ChildrenRepository;
import com.parentapp.parent_assist.util.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class EventActivityService {

    private final EventActivityRepository eventActivityRepository;
    private final ActivitiesRepository activitiesRepository;
    private final ChildrenRepository childrenRepository;

    public EventActivityService(final EventActivityRepository eventActivityRepository,
            final ActivitiesRepository activitiesRepository,
            final ChildrenRepository childrenRepository) {
        this.eventActivityRepository = eventActivityRepository;
        this.activitiesRepository = activitiesRepository;
        this.childrenRepository = childrenRepository;
    }

    public List<EventActivityDTO> findAll() {
        final List<EventActivity> eventActivitys = eventActivityRepository.findAll(Sort.by("id"));
        return eventActivitys.stream()
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
        eventActivityDTO.setDificulty(eventActivity.getDificulty());
        eventActivityDTO.setInterest(eventActivity.getInterest());
        eventActivityDTO.setFeedback(eventActivity.getFeedback());
        eventActivityDTO.setActivityId(eventActivity.getActivityId() == null ? null : eventActivity.getActivityId().getId());
        eventActivityDTO.setChildId(eventActivity.getChildId() == null ? null : eventActivity.getChildId().getId());
        return eventActivityDTO;
    }

    private EventActivity mapToEntity(final EventActivityDTO eventActivityDTO,
            final EventActivity eventActivity) {
        eventActivity.setDate(eventActivityDTO.getDate());
        eventActivity.setDificulty(eventActivityDTO.getDificulty());
        eventActivity.setInterest(eventActivityDTO.getInterest());
        eventActivity.setFeedback(eventActivityDTO.getFeedback());
        final Activities activityId = eventActivityDTO.getActivityId() == null ? null : activitiesRepository.findById(eventActivityDTO.getActivityId())
                .orElseThrow(() -> new NotFoundException("activityId not found"));
        eventActivity.setActivityId(activityId);
        final Children childId = eventActivityDTO.getChildId() == null ? null : childrenRepository.findById(eventActivityDTO.getChildId())
                .orElseThrow(() -> new NotFoundException("childId not found"));
        eventActivity.setChildId(childId);
        return eventActivity;
    }

    public boolean idExists(final String id) {
        return eventActivityRepository.existsByIdIgnoreCase(id);
    }

}
