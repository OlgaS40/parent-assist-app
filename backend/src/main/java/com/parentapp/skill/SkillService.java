package com.parentapp.skill;

import com.parentapp.util.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class SkillService {

    private final SkillRepository skillRepository;

    public SkillService(final SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public List<SkillDTO> findAll() {
        final List<Skill> skills = skillRepository.findAll();
        return skills.stream()
                .map((skill) -> mapToDTO(skill, new SkillDTO()))
                .collect(Collectors.toList());
    }

    public SkillDTO get(final String id) {
        return skillRepository.findById(id)
                .map(skills -> mapToDTO(skills, new SkillDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public String create(final SkillDTO skillDTO) {
        final Skill skill = new Skill();
        mapToEntity(skillDTO, skill);
        skill.setId(skillDTO.getId());
        return skillRepository.save(skill).getId();
    }

    public void update(final String id, final SkillDTO skillDTO) {
        final Skill skill = skillRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(skillDTO, skill);
        skillRepository.save(skill);
    }

    public void delete(final String id) {
        skillRepository.deleteById(id);
    }

    private SkillDTO mapToDTO(final Skill skill, final SkillDTO skillDTO) {
        skillDTO.setId(skill.getId());
        skillDTO.setName(skill.getName());
        skillDTO.setDescription(skill.getDescription());
        return skillDTO;
    }

    private Skill mapToEntity(final SkillDTO skillDTO, final Skill skill) {
        skill.setName(skillDTO.getName());
        skill.setDescription(skillDTO.getDescription());
        return skill;
    }

    public boolean idExists(final String id) {
        return skillRepository.existsByIdIgnoreCase(id);
    }

}
