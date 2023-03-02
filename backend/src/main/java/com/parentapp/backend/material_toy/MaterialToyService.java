package com.parentapp.backend.material_toy;

import com.parentapp.backend.util.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class MaterialToyService {

    private final MaterialToyRepository materialToyRepository;

    public MaterialToyService(final MaterialToyRepository materialToyRepository) {
        this.materialToyRepository = materialToyRepository;
    }

    public List<MaterialToyDTO> findAll() {
        final List<MaterialToy> materialsToys = materialToyRepository.findAll(Sort.by("id"));
        return materialsToys.stream()
                .map((materialToy) -> mapToDTO(materialToy, new MaterialToyDTO()))
                .collect(Collectors.toList());
    }

    public MaterialToyDTO get(final String id) {
        return materialToyRepository.findById(id)
                .map(materialToy -> mapToDTO(materialToy, new MaterialToyDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public String create(final MaterialToyDTO materialToyDTO) {
        final MaterialToy materialToy = new MaterialToy();
        mapToEntity(materialToyDTO, materialToy);
        materialToy.setId(materialToyDTO.getId());
        return materialToyRepository.save(materialToy).getId();
    }

    public void update(final String id, final MaterialToyDTO materialToyDTO) {
        final MaterialToy materialToy = materialToyRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(materialToyDTO, materialToy);
        materialToyRepository.save(materialToy);
    }

    public void delete(final String id) {
        materialToyRepository.deleteById(id);
    }

    private MaterialToyDTO mapToDTO(final MaterialToy materialToy,
                                    final MaterialToyDTO materialToyDTO) {
        materialToyDTO.setId(materialToy.getId());
        materialToyDTO.setName(materialToy.getName());
        materialToyDTO.setCategory(materialToy.getCategory());
        materialToyDTO.setDescription(materialToy.getDescription());
        materialToyDTO.setImageUrl(materialToy.getImageUrl());
        return materialToyDTO;
    }

    private MaterialToy mapToEntity(final MaterialToyDTO materialToyDTO,
                                    final MaterialToy materialToy) {
        materialToy.setName(materialToyDTO.getName());
        materialToy.setCategory(materialToyDTO.getCategory());
        materialToy.setDescription(materialToyDTO.getDescription());
        materialToy.setImageUrl(materialToyDTO.getImageUrl());
        return materialToy;
    }

    public boolean idExists(final String id) {
        return materialToyRepository.existsByIdIgnoreCase(id);
    }

}
