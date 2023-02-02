package com.parentapp.parent_assist.parents;

import com.parentapp.parent_assist.util.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ParentsService {

    private final ParentsRepository parentsRepository;

    public ParentsService(final ParentsRepository parentsRepository) {
        this.parentsRepository = parentsRepository;
    }

    public List<ParentsDTO> findAll() {
        final List<Parents> parentss = parentsRepository.findAll(Sort.by("id"));
        return parentss.stream()
                .map((parents) -> mapToDTO(parents, new ParentsDTO()))
                .collect(Collectors.toList());
    }

    public ParentsDTO get(final String id) {
        return parentsRepository.findById(id)
                .map(parents -> mapToDTO(parents, new ParentsDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public String create(final ParentsDTO parentsDTO) {
        final Parents parents = new Parents();
        mapToEntity(parentsDTO, parents);
        parents.setId(parentsDTO.getId());
        return parentsRepository.save(parents).getId();
    }

    public void update(final String id, final ParentsDTO parentsDTO) {
        final Parents parents = parentsRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(parentsDTO, parents);
        parentsRepository.save(parents);
    }

    public void delete(final String id) {
        parentsRepository.deleteById(id);
    }

    private ParentsDTO mapToDTO(final Parents parents, final ParentsDTO parentsDTO) {
        parentsDTO.setId(parents.getId());
        parentsDTO.setName(parents.getName());
        parentsDTO.setSurname(parents.getSurname());
        parentsDTO.setDateOfBirth(parents.getDateOfBirth());
        parentsDTO.setLanguage(parents.getLanguage());
        parentsDTO.setCountry(parents.getCountry());
        parentsDTO.setCity(parents.getCity());
        parentsDTO.setAddress(parents.getAddress());
        parentsDTO.setPostalCode(parents.getPostalCode());
        return parentsDTO;
    }

    private Parents mapToEntity(final ParentsDTO parentsDTO, final Parents parents) {
        parents.setName(parentsDTO.getName());
        parents.setSurname(parentsDTO.getSurname());
        parents.setDateOfBirth(parentsDTO.getDateOfBirth());
        parents.setLanguage(parentsDTO.getLanguage());
        parents.setCountry(parentsDTO.getCountry());
        parents.setCity(parentsDTO.getCity());
        parents.setAddress(parentsDTO.getAddress());
        parents.setPostalCode(parentsDTO.getPostalCode());
        return parents;
    }

    public boolean idExists(final String id) {
        return parentsRepository.existsByIdIgnoreCase(id);
    }

}
