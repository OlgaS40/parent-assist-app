package com.parentapp.backend.activity;

import com.parentapp.backend.material_toy.MaterialToy;
import com.parentapp.backend.material_toy.MaterialToyRepository;
import com.parentapp.backend.milestone.Milestone;
import com.parentapp.backend.milestone.MilestoneRepository;
import com.parentapp.backend.place.Place;
import com.parentapp.backend.place.PlaceRepository;
import com.parentapp.backend.skill.Skill;
import com.parentapp.backend.skill.SkillRepository;
import com.parentapp.backend.util.NotFoundException;
import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Transactional
@Service
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final MilestoneRepository milestoneRepository;
    private final MaterialToyRepository materialToyRepository;
    private final PlaceRepository placeRepository;
    private final SkillRepository skillRepository;

    public ActivityService(final ActivityRepository activityRepository,
                           final MilestoneRepository milestoneRepository,
                           final MaterialToyRepository materialToyRepository,
                           final PlaceRepository placeRepository, final SkillRepository skillRepository) {
        this.activityRepository = activityRepository;
        this.milestoneRepository = milestoneRepository;
        this.materialToyRepository = materialToyRepository;
        this.placeRepository = placeRepository;
        this.skillRepository = skillRepository;
    }

    public List<ActivityDTO> findAll() {
        final List<Activity> activities = activityRepository.findAll(Sort.by("id"));
        return activities.stream()
                .map((activity) -> mapToDTO(activity, new ActivityDTO()))
                .collect(Collectors.toList());
    }

    public ActivityDTO get(final String id) {
        return activityRepository.findById(id)
                .map(activities -> mapToDTO(activities, new ActivityDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public String create(final ActivityDTO activityDTO) {
        final Activity activity = new Activity();
        mapToEntity(activityDTO, activity);
        activity.setId(activityDTO.getId());
        return activityRepository.save(activity).getId();
    }

    public void update(final String id, final ActivityDTO activityDTO) {
        final Activity activity = activityRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(activityDTO, activity);
        activityRepository.save(activity);
    }

    public void delete(final String id) {
        activityRepository.deleteById(id);
    }

    private ActivityDTO mapToDTO(final Activity activity, final ActivityDTO activityDTO) {
        activityDTO.setId(activity.getId());
        activityDTO.setName(activity.getName());
        activityDTO.setHowTo(activity.getHowTo());
        activityDTO.setRules(activity.getRules());
        activityDTO.setDescription(activity.getDescription());
        activityDTO.setTime(activity.getTime());
        activityDTO.setAgeUnit(activity.getAgeUnit());
        activityDTO.setAgeFrom(activity.getAgeFrom());
        activityDTO.setAgeTo(activity.getAgeTo());
        activityDTO.setActivityMilestones(activity.getActivityMilestones() == null ? null : activity.getActivityMilestones().stream()
                .map(Milestone::getId)
                .collect(Collectors.toList()));
        activityDTO.setActivityMaterials(activity.getActivityMaterials() == null ? null : activity.getActivityMaterials().stream()
                .map(MaterialToy::getId)
                .collect(Collectors.toList()));
        activityDTO.setActivityPlaces(activity.getActivityPlaces() == null ? null : activity.getActivityPlaces().stream()
                .map(Place::getId)
                .collect(Collectors.toList()));
        activityDTO.setActivitySkills(activity.getActivitySkills() == null ? null : activity.getActivitySkills().stream()
                .map(Skill::getId)
                .collect(Collectors.toList()));
        return activityDTO;
    }

    private Activity mapToEntity(final ActivityDTO activityDTO, final Activity activity) {
        activity.setName(activityDTO.getName());
        activity.setHowTo(activityDTO.getHowTo());
        activity.setRules(activityDTO.getRules());
        activity.setDescription(activityDTO.getDescription());
        activity.setTime(activityDTO.getTime());
        activity.setAgeUnit(activityDTO.getAgeUnit());
        activity.setAgeFrom(activityDTO.getAgeFrom());
        activity.setAgeTo(activityDTO.getAgeTo());
        final List<Milestone> activitiesMilestones = milestoneRepository.findAllById(
                activityDTO.getActivityMilestones() == null ? Collections.emptyList() : activityDTO.getActivityMilestones());
        if (activitiesMilestones.size() != (activityDTO.getActivityMilestones() == null ? 0 : activityDTO.getActivityMilestones().size())) {
            throw new NotFoundException("one of activitiesMilestones not found");
        }
        activity.setActivityMilestones(new HashSet<>(activitiesMilestones));
        final List<MaterialToy> activityMaterials = materialToyRepository.findAllById(
                activityDTO.getActivityMaterials() == null ? Collections.emptyList() : activityDTO.getActivityMaterials());
        if (activityMaterials.size() != (activityDTO.getActivityMaterials() == null ? 0 : activityDTO.getActivityMaterials().size())) {
            throw new NotFoundException("one of activityMaterials not found");
        }
        activity.setActivityMaterials(new HashSet<>(activityMaterials));
        final List<Place> activityPlaces = placeRepository.findAllById(
                activityDTO.getActivityPlaces() == null ? Collections.emptyList() : activityDTO.getActivityPlaces());
        if (activityPlaces.size() != (activityDTO.getActivityPlaces() == null ? 0 : activityDTO.getActivityPlaces().size())) {
            throw new NotFoundException("one of activityPlaces not found");
        }
        activity.setActivityPlaces(new HashSet<>(activityPlaces));
        final List<Skill> activitySkills = skillRepository.findAllById(
                activityDTO.getActivitySkills() == null ? Collections.emptyList() : activityDTO.getActivitySkills());
        if (activitySkills.size() != (activityDTO.getActivitySkills() == null ? 0 : activityDTO.getActivitySkills().size())) {
            throw new NotFoundException("one of activitySkills not found");
        }
        activity.setActivitySkills(new HashSet<>(activitySkills));
        return activity;
    }

    public boolean idExists(final String id) {
        return activityRepository.existsByIdIgnoreCase(id);
    }

}
