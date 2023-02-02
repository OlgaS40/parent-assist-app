package com.parentapp.parent_assist.skills;

import com.parentapp.parent_assist.util.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class SkillsService {

    private final SkillsRepository skillsRepository;

    public SkillsService(final SkillsRepository skillsRepository) {
        this.skillsRepository = skillsRepository;
    }

    public List<SkillsDTO> findAll() {
        final List<Skills> skillss = skillsRepository.findAll(Sort.by("id"));
        return skillss.stream()
                .map((skills) -> mapToDTO(skills, new SkillsDTO()))
                .collect(Collectors.toList());
    }

    public SkillsDTO get(final String id) {
        return skillsRepository.findById(id)
                .map(skills -> mapToDTO(skills, new SkillsDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public String create(final SkillsDTO skillsDTO) {
        final Skills skills = new Skills();
        mapToEntity(skillsDTO, skills);
        skills.setId(skillsDTO.getId());
        return skillsRepository.save(skills).getId();
    }

    public void update(final String id, final SkillsDTO skillsDTO) {
        final Skills skills = skillsRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(skillsDTO, skills);
        skillsRepository.save(skills);
    }

    public void delete(final String id) {
        skillsRepository.deleteById(id);
    }

    private SkillsDTO mapToDTO(final Skills skills, final SkillsDTO skillsDTO) {
        skillsDTO.setId(skills.getId());
        skillsDTO.setName(skills.getName());
        skillsDTO.setDescription(skills.getDescription());
        return skillsDTO;
    }

    private Skills mapToEntity(final SkillsDTO skillsDTO, final Skills skills) {
        skills.setName(skillsDTO.getName());
        skills.setDescription(skillsDTO.getDescription());
        return skills;
    }

    public boolean idExists(final String id) {
        return skillsRepository.existsByIdIgnoreCase(id);
    }

}
