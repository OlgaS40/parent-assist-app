package com.parentapp.backend.parent;

import com.parentapp.backend.util.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ParentService {

    private final ParentRepository parentRepository;

    public ParentService(final ParentRepository parentRepository) {
        this.parentRepository = parentRepository;
    }

    public List<ParentDTO> findAll() {
        final List<Parent> parents = parentRepository.findAll(Sort.by("id"));
        return parents.stream()
                .map((parent) -> mapToDTO(parent, new ParentDTO()))
                .collect(Collectors.toList());
    }

    public ParentDTO get(final String id) {
        return parentRepository.findById(id)
                .map(parent -> mapToDTO(parent, new ParentDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public String create(final ParentDTO parentDTO) {
        final Parent parent = new Parent();
        mapToEntity(parentDTO, parent);
        parent.setId(parentDTO.getId());
        return parentRepository.save(parent).getId();
    }

    public void update(final String id, final ParentDTO parentDTO) {
        final Parent parent = parentRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(parentDTO, parent);
        parentRepository.save(parent);
    }

    public void delete(final String id) {
        parentRepository.deleteById(id);
    }

    private ParentDTO mapToDTO(final Parent parent, final ParentDTO parentDTO) {
        parentDTO.setId(parent.getId());
        parentDTO.setName(parent.getName());
        parentDTO.setSurname(parent.getSurname());
        parentDTO.setDateOfBirth(parent.getDateOfBirth());
        parentDTO.setLanguage(parent.getLanguage());
        parentDTO.setCountry(parent.getCountry());
        parentDTO.setCity(parent.getCity());
        parentDTO.setAddress(parent.getAddress());
        parentDTO.setPostalCode(parent.getPostalCode());
        return parentDTO;
    }

    private Parent mapToEntity(final ParentDTO parentDTO, final Parent parent) {
        parent.setName(parentDTO.getName());
        parent.setSurname(parentDTO.getSurname());
        parent.setDateOfBirth(parentDTO.getDateOfBirth());
        parent.setLanguage(parentDTO.getLanguage());
        parent.setCountry(parentDTO.getCountry());
        parent.setCity(parentDTO.getCity());
        parent.setAddress(parentDTO.getAddress());
        parent.setPostalCode(parentDTO.getPostalCode());
        return parent;
    }

    public boolean idExists(final String id) {
        return parentRepository.existsByIdIgnoreCase(id);
    }

}
