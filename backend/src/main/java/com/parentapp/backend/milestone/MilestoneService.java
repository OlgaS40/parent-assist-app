package com.parentapp.backend.milestone;

import com.parentapp.backend.activity.Activity;
import com.parentapp.backend.activity.ActivityRepository;
import com.parentapp.backend.util.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MilestoneService {
    private final MilestoneRepository milestoneRepository;
    private final ActivityRepository activityRepository;

    public MilestoneService(final MilestoneRepository milestoneRepository, ActivityRepository activityRepository) {
        this.milestoneRepository = milestoneRepository;
        this.activityRepository = activityRepository;
    }

    public List<MilestoneDTO> findAll() {
        final List<Milestone> milestones = milestoneRepository.findAll();
        return milestones.stream()
                .map((milestone) -> mapToDTO(milestone, new MilestoneDTO()))
                .collect(Collectors.toList());
    }

    public MilestoneDTO get(final String id) {
        return milestoneRepository.findById(id)
                .map(milestone -> mapToDTO(milestone, new MilestoneDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public String create(final MilestoneDTO milestoneDTO) {
        final Milestone milestone = new Milestone();
        mapToEntity(milestoneDTO, milestone);
        milestone.setId(milestoneDTO.getId());
        return milestoneRepository.save(milestone).getId();
    }

    public void update(final String id, final MilestoneDTO milestoneDTO) {
        final Milestone milestone = milestoneRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(milestoneDTO, milestone);
        milestoneRepository.save(milestone);
    }

    public void delete(final String id) {
        milestoneRepository.deleteById(id);
    }

    private MilestoneDTO mapToDTO(final Milestone milestone,
                                  final MilestoneDTO milestoneDTO) {
        milestoneDTO.setId(milestone.getId());
        milestoneDTO.setName(milestone.getName());
        milestoneDTO.setDescription(milestone.getDescription());
        milestoneDTO.setAgeUnit(milestone.getAgeUnit());
        milestoneDTO.setAgeFrom(milestone.getAgeFrom());
        milestoneDTO.setAgeTo(milestone.getAgeTo());
        milestoneDTO.setActivities(milestone.getMilestoneActivities() == null ?
                null : milestone.getMilestoneActivities().stream()
                .map(Activity::getId)
                .collect(Collectors.toSet()));
        return milestoneDTO;
    }

    private Milestone mapToEntity(final MilestoneDTO milestoneDTO,
                                  final Milestone milestone) {
        milestone.setId(milestoneDTO.getId());
        milestone.setName(milestoneDTO.getName());
        milestone.setDescription(milestoneDTO.getDescription());
        milestone.setAgeUnit(milestoneDTO.getAgeUnit());
        milestone.setAgeFrom(milestoneDTO.getAgeFrom());
        milestone.setAgeTo(milestoneDTO.getAgeTo());
        milestone.setMilestoneActivities(milestoneDTO.getActivities()==null ? null: milestoneDTO.getActivities().stream()
                .map(id -> activityRepository.findById(id).orElseThrow(NotFoundException::new))
                .collect(Collectors.toSet()));
        return milestone;
    }

    public boolean idExists(final String id) {
        return milestoneRepository.existsByIdIgnoreCase(id);
    }
}
