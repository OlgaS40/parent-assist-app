package com.parentapp.parent_assist.event_milestone;

import com.parentapp.parent_assist.children.Children;
import com.parentapp.parent_assist.children.ChildrenRepository;
import com.parentapp.parent_assist.milestones.Milestones;
import com.parentapp.parent_assist.milestones.MilestonesRepository;
import com.parentapp.parent_assist.util.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class EventMilestoneService {

    private final EventMilestoneRepository eventMilestoneRepository;
    private final MilestonesRepository milestonesRepository;
    private final ChildrenRepository childrenRepository;

    public EventMilestoneService(final EventMilestoneRepository eventMilestoneRepository,
            final MilestonesRepository milestonesRepository,
            final ChildrenRepository childrenRepository) {
        this.eventMilestoneRepository = eventMilestoneRepository;
        this.milestonesRepository = milestonesRepository;
        this.childrenRepository = childrenRepository;
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
        eventMilestoneDTO.setMilestoneId(eventMilestone.getMilestoneId() == null ? null : eventMilestone.getMilestoneId().getId());
        eventMilestoneDTO.setChildId(eventMilestone.getChildId() == null ? null : eventMilestone.getChildId().getId());
        return eventMilestoneDTO;
    }

    private EventMilestone mapToEntity(final EventMilestoneDTO eventMilestoneDTO,
            final EventMilestone eventMilestone) {
        eventMilestone.setDate(eventMilestoneDTO.getDate());
        final Milestones milestoneId = eventMilestoneDTO.getMilestoneId() == null ? null : milestonesRepository.findById(eventMilestoneDTO.getMilestoneId())
                .orElseThrow(() -> new NotFoundException("milestoneId not found"));
        eventMilestone.setMilestoneId(milestoneId);
        final Children childId = eventMilestoneDTO.getChildId() == null ? null : childrenRepository.findById(eventMilestoneDTO.getChildId())
                .orElseThrow(() -> new NotFoundException("childId not found"));
        eventMilestone.setChildId(childId);
        return eventMilestone;
    }

    public boolean idExists(final String id) {
        return eventMilestoneRepository.existsByIdIgnoreCase(id);
    }

}
