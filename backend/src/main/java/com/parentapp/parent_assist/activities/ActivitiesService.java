package com.parentapp.parent_assist.activities;

import com.parentapp.parent_assist.materials_toys.MaterialsToys;
import com.parentapp.parent_assist.materials_toys.MaterialsToysRepository;
import com.parentapp.parent_assist.milestones.Milestones;
import com.parentapp.parent_assist.milestones.MilestonesRepository;
import com.parentapp.parent_assist.place.Place;
import com.parentapp.parent_assist.place.PlaceRepository;
import com.parentapp.parent_assist.skills.Skills;
import com.parentapp.parent_assist.skills.SkillsRepository;
import com.parentapp.parent_assist.util.NotFoundException;
import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Transactional
@Service
public class ActivitiesService {

    private final ActivitiesRepository activitiesRepository;
    private final MilestonesRepository milestonesRepository;
    private final MaterialsToysRepository materialsToysRepository;
    private final PlaceRepository placeRepository;
    private final SkillsRepository skillsRepository;

    public ActivitiesService(final ActivitiesRepository activitiesRepository,
            final MilestonesRepository milestonesRepository,
            final MaterialsToysRepository materialsToysRepository,
            final PlaceRepository placeRepository, final SkillsRepository skillsRepository) {
        this.activitiesRepository = activitiesRepository;
        this.milestonesRepository = milestonesRepository;
        this.materialsToysRepository = materialsToysRepository;
        this.placeRepository = placeRepository;
        this.skillsRepository = skillsRepository;
    }

    public List<ActivitiesDTO> findAll() {
        final List<Activities> activitiess = activitiesRepository.findAll(Sort.by("id"));
        return activitiess.stream()
                .map((activities) -> mapToDTO(activities, new ActivitiesDTO()))
                .collect(Collectors.toList());
    }

    public ActivitiesDTO get(final String id) {
        return activitiesRepository.findById(id)
                .map(activities -> mapToDTO(activities, new ActivitiesDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public String create(final ActivitiesDTO activitiesDTO) {
        final Activities activities = new Activities();
        mapToEntity(activitiesDTO, activities);
        activities.setId(activitiesDTO.getId());
        return activitiesRepository.save(activities).getId();
    }

    public void update(final String id, final ActivitiesDTO activitiesDTO) {
        final Activities activities = activitiesRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(activitiesDTO, activities);
        activitiesRepository.save(activities);
    }

    public void delete(final String id) {
        activitiesRepository.deleteById(id);
    }

    private ActivitiesDTO mapToDTO(final Activities activities, final ActivitiesDTO activitiesDTO) {
        activitiesDTO.setId(activities.getId());
        activitiesDTO.setName(activities.getName());
        activitiesDTO.setHowTo(activities.getHowTo());
        activitiesDTO.setRules(activities.getRules());
        activitiesDTO.setDescription(activities.getDescription());
        activitiesDTO.setTime(activities.getTime());
        activitiesDTO.setAgeUnit(activities.getAgeUnit());
        activitiesDTO.setAgeFrom(activities.getAgeFrom());
        activitiesDTO.setAgeTo(activities.getAgeTo());
        activitiesDTO.setActivitiesMilestoness(activities.getActivitiesMilestonesMilestoness() == null ? null : activities.getActivitiesMilestonesMilestoness().stream()
                .map(milestones -> milestones.getId())
                .collect(Collectors.toList()));
        activitiesDTO.setActivityMaterialss(activities.getActivityMaterialsMaterialsToyss() == null ? null : activities.getActivityMaterialsMaterialsToyss().stream()
                .map(materialsToys -> materialsToys.getId())
                .collect(Collectors.toList()));
        activitiesDTO.setActivityPlacess(activities.getActivityPlacesPlaces() == null ? null : activities.getActivityPlacesPlaces().stream()
                .map(place -> place.getId())
                .collect(Collectors.toList()));
        activitiesDTO.setActivitySkillss(activities.getActivitySkillsSkillss() == null ? null : activities.getActivitySkillsSkillss().stream()
                .map(skills -> skills.getId())
                .collect(Collectors.toList()));
        return activitiesDTO;
    }

    private Activities mapToEntity(final ActivitiesDTO activitiesDTO, final Activities activities) {
        activities.setName(activitiesDTO.getName());
        activities.setHowTo(activitiesDTO.getHowTo());
        activities.setRules(activitiesDTO.getRules());
        activities.setDescription(activitiesDTO.getDescription());
        activities.setTime(activitiesDTO.getTime());
        activities.setAgeUnit(activitiesDTO.getAgeUnit());
        activities.setAgeFrom(activitiesDTO.getAgeFrom());
        activities.setAgeTo(activitiesDTO.getAgeTo());
        final List<Milestones> activitiesMilestoness = milestonesRepository.findAllById(
                activitiesDTO.getActivitiesMilestoness() == null ? Collections.emptyList() : activitiesDTO.getActivitiesMilestoness());
        if (activitiesMilestoness.size() != (activitiesDTO.getActivitiesMilestoness() == null ? 0 : activitiesDTO.getActivitiesMilestoness().size())) {
            throw new NotFoundException("one of activitiesMilestoness not found");
        }
        activities.setActivitiesMilestonesMilestoness(activitiesMilestoness.stream().collect(Collectors.toSet()));
        final List<MaterialsToys> activityMaterialss = materialsToysRepository.findAllById(
                activitiesDTO.getActivityMaterialss() == null ? Collections.emptyList() : activitiesDTO.getActivityMaterialss());
        if (activityMaterialss.size() != (activitiesDTO.getActivityMaterialss() == null ? 0 : activitiesDTO.getActivityMaterialss().size())) {
            throw new NotFoundException("one of activityMaterialss not found");
        }
        activities.setActivityMaterialsMaterialsToyss(activityMaterialss.stream().collect(Collectors.toSet()));
        final List<Place> activityPlacess = placeRepository.findAllById(
                activitiesDTO.getActivityPlacess() == null ? Collections.emptyList() : activitiesDTO.getActivityPlacess());
        if (activityPlacess.size() != (activitiesDTO.getActivityPlacess() == null ? 0 : activitiesDTO.getActivityPlacess().size())) {
            throw new NotFoundException("one of activityPlacess not found");
        }
        activities.setActivityPlacesPlaces(activityPlacess.stream().collect(Collectors.toSet()));
        final List<Skills> activitySkillss = skillsRepository.findAllById(
                activitiesDTO.getActivitySkillss() == null ? Collections.emptyList() : activitiesDTO.getActivitySkillss());
        if (activitySkillss.size() != (activitiesDTO.getActivitySkillss() == null ? 0 : activitiesDTO.getActivitySkillss().size())) {
            throw new NotFoundException("one of activitySkillss not found");
        }
        activities.setActivitySkillsSkillss(activitySkillss.stream().collect(Collectors.toSet()));
        return activities;
    }

    public boolean idExists(final String id) {
        return activitiesRepository.existsByIdIgnoreCase(id);
    }

}
