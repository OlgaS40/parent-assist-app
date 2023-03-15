package com.parentapp.event_milestone;

import com.parentapp.child.Child;
import com.parentapp.child.ChildRepository;
import com.parentapp.milestone.Milestone;
import com.parentapp.milestone.MilestoneRepository;
import com.parentapp.util.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class EventMilestoneService {

    private final EventMilestoneRepository eventMilestoneRepository;
    private final MilestoneRepository milestoneRepository;
    private final ChildRepository childRepository;

    public EventMilestoneService(final EventMilestoneRepository eventMilestoneRepository,
            final MilestoneRepository milestoneRepository,
            final ChildRepository childRepository) {
        this.eventMilestoneRepository = eventMilestoneRepository;
        this.milestoneRepository = milestoneRepository;
        this.childRepository = childRepository;
    }

    public List<EventMilestoneDTO> findAll() {
        final List<EventMilestone> eventMilestones = eventMilestoneRepository.findAll(Sort.by("id"));
        return eventMilestones.stream()
                .map((eventMilestone) -> mapToDTO(eventMilestone, new EventMilestoneDTO()))
                .collect(Collectors.toList());
    }

    public EventMilestoneDTO get(final String id) {
        return eventMilestoneRepository.findById(id)
                .map(eventMilestone -> mapToDTO(eventMilestone, new EventMilestoneDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public String create(final EventMilestoneDTO eventMilestoneDTO) {
        final EventMilestone eventMilestone = new EventMilestone();
        mapToEntity(eventMilestoneDTO, eventMilestone);
        eventMilestone.setId(eventMilestoneDTO.getId());
        return eventMilestoneRepository.save(eventMilestone).getId();
    }

    public void update(final String id, final EventMilestoneDTO eventMilestoneDTO) {
        final EventMilestone eventMilestone = eventMilestoneRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(eventMilestoneDTO, eventMilestone);
        eventMilestoneRepository.save(eventMilestone);
    }

    public void delete(final String id) {
        eventMilestoneRepository.deleteById(id);
    }

    private EventMilestoneDTO mapToDTO(final EventMilestone eventMilestone,
            final EventMilestoneDTO eventMilestoneDTO) {
        eventMilestoneDTO.setId(eventMilestone.getId());
        eventMilestoneDTO.setDate(eventMilestone.getDate());
        eventMilestoneDTO.setMilestoneId(eventMilestone.getMilestone() == null ? null : eventMilestone.getMilestone().getId());
        eventMilestoneDTO.setChildId(eventMilestone.getChild() == null ? null : eventMilestone.getChild().getId());
        return eventMilestoneDTO;
    }

    private EventMilestone mapToEntity(final EventMilestoneDTO eventMilestoneDTO,
            final EventMilestone eventMilestone) {
        eventMilestone.setDate(eventMilestoneDTO.getDate());
        final Milestone milestoneId = eventMilestoneDTO.getMilestoneId() == null ? null : milestoneRepository.findById(eventMilestoneDTO.getMilestoneId())
                .orElseThrow(() -> new NotFoundException("milestoneId not found"));
        eventMilestone.setMilestone(milestoneId);
        final Child childId = eventMilestoneDTO.getChildId() == null ? null : childRepository.findById(eventMilestoneDTO.getChildId())
                .orElseThrow(() -> new NotFoundException("childId not found"));
        eventMilestone.setChild(childId);
        return eventMilestone;
    }

    public boolean idExists(final String id) {
        return eventMilestoneRepository.existsByIdIgnoreCase(id);
    }

}
