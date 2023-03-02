package com.parentapp.backend.skill;

import com.parentapp.backend.util.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class SkillService {

    private final SkillRepository skillRepository;

    public SkillService(final SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public List<SkillDTO> findAll() {
        final List<Skill> skillsses = skillRepository.findAll(Sort.by("id"));
        return skillsses.stream()
                .map((skills) -> mapToDTO(skills, new SkillDTO()))
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
