package com.parentapp.parent_assist.children;

import com.parentapp.parent_assist.util.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ChildrenService {

    private final ChildrenRepository childrenRepository;

    public ChildrenService(final ChildrenRepository childrenRepository) {
        this.childrenRepository = childrenRepository;
    }

    public List<ChildrenDTO> findAll() {
        final List<Children> childrens = childrenRepository.findAll(Sort.by("id"));
        return childrens.stream()
                .map((children) -> mapToDTO(children, new ChildrenDTO()))
                .collect(Collectors.toList());
    }

    public ChildrenDTO get(final String id) {
        return childrenRepository.findById(id)
                .map(children -> mapToDTO(children, new ChildrenDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public String create(final ChildrenDTO childrenDTO) {
        final Children children = new Children();
        mapToEntity(childrenDTO, children);
        children.setId(childrenDTO.getId());
        return childrenRepository.save(children).getId();
    }

    public void update(final String id, final ChildrenDTO childrenDTO) {
        final Children children = childrenRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(childrenDTO, children);
        childrenRepository.save(children);
    }

    public void delete(final String id) {
        childrenRepository.deleteById(id);
    }

    private ChildrenDTO mapToDTO(final Children children, final ChildrenDTO childrenDTO) {
        childrenDTO.setId(children.getId());
        childrenDTO.setName(children.getName());
        childrenDTO.setGender(children.getGender());
        childrenDTO.setDateOfBirth(children.getDateOfBirth());
        childrenDTO.setImageUrl(children.getImageUrl());
        return childrenDTO;
    }

    private Children mapToEntity(final ChildrenDTO childrenDTO, final Children children) {
        children.setName(childrenDTO.getName());
        children.setGender(childrenDTO.getGender());
        children.setDateOfBirth(childrenDTO.getDateOfBirth());
        children.setImageUrl(childrenDTO.getImageUrl());
        return children;
    }

    public boolean idExists(final String id) {
        return childrenRepository.existsByIdIgnoreCase(id);
    }

}
