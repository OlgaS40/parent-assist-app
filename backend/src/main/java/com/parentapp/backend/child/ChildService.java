package com.parentapp.backend.child;

import com.parentapp.backend.util.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ChildService {

    private final ChildRepository childRepository;

    public ChildService(final ChildRepository childRepository) {
        this.childRepository = childRepository;
    }

    public List<ChildDTO> findAll() {
        final List<Child> children = childRepository.findAll(Sort.by("id"));
        return children.stream()
                .map((child) -> mapToDTO(child, new ChildDTO()))
                .collect(Collectors.toList());
    }

    public ChildDTO get(final String id) {
        return childRepository.findById(id)
                .map(children -> mapToDTO(children, new ChildDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public String create(final ChildDTO childDTO) {
        final Child child = new Child();
        mapToEntity(childDTO, child);
        child.setId(childDTO.getId());
        return childRepository.save(child).getId();
    }

    public void update(final String id, final ChildDTO childDTO) {
        final Child child = childRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(childDTO, child);
        childRepository.save(child);
    }

    public void delete(final String id) {
        childRepository.deleteById(id);
    }

    private ChildDTO mapToDTO(final Child child, final ChildDTO childDTO) {
        childDTO.setId(child.getId());
        childDTO.setName(child.getName());
        childDTO.setGender(child.getGender());
        childDTO.setDateOfBirth(child.getDateOfBirth());
        childDTO.setImageUrl(child.getImageUrl());
        return childDTO;
    }

    private Child mapToEntity(final ChildDTO childDTO, final Child child) {
        child.setName(childDTO.getName());
        child.setGender(childDTO.getGender());
        child.setDateOfBirth(childDTO.getDateOfBirth());
        child.setImageUrl(childDTO.getImageUrl());
        return child;
    }

    public boolean idExists(final String id) {
        return childRepository.existsByIdIgnoreCase(id);
    }

}
