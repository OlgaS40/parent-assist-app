package com.parentapp.parent_assist.materials_toys;

import com.parentapp.parent_assist.util.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class MaterialsToysService {

    private final MaterialsToysRepository materialsToysRepository;

    public MaterialsToysService(final MaterialsToysRepository materialsToysRepository) {
        this.materialsToysRepository = materialsToysRepository;
    }

    public List<MaterialsToysDTO> findAll() {
        final List<MaterialsToys> materialsToyss = materialsToysRepository.findAll(Sort.by("id"));
        return materialsToyss.stream()
                .map((materialsToys) -> mapToDTO(materialsToys, new MaterialsToysDTO()))
                .collect(Collectors.toList());
    }

    public MaterialsToysDTO get(final String id) {
        return materialsToysRepository.findById(id)
                .map(materialsToys -> mapToDTO(materialsToys, new MaterialsToysDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public String create(final MaterialsToysDTO materialsToysDTO) {
        final MaterialsToys materialsToys = new MaterialsToys();
        mapToEntity(materialsToysDTO, materialsToys);
        materialsToys.setId(materialsToysDTO.getId());
        return materialsToysRepository.save(materialsToys).getId();
    }

    public void update(final String id, final MaterialsToysDTO materialsToysDTO) {
        final MaterialsToys materialsToys = materialsToysRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(materialsToysDTO, materialsToys);
        materialsToysRepository.save(materialsToys);
    }

    public void delete(final String id) {
        materialsToysRepository.deleteById(id);
    }

    private MaterialsToysDTO mapToDTO(final MaterialsToys materialsToys,
            final MaterialsToysDTO materialsToysDTO) {
        materialsToysDTO.setId(materialsToys.getId());
        materialsToysDTO.setName(materialsToys.getName());
        materialsToysDTO.setCategory(materialsToys.getCategory());
        materialsToysDTO.setDescription(materialsToys.getDescription());
        materialsToysDTO.setImageUrl(materialsToys.getImageUrl());
        return materialsToysDTO;
    }

    private MaterialsToys mapToEntity(final MaterialsToysDTO materialsToysDTO,
            final MaterialsToys materialsToys) {
        materialsToys.setName(materialsToysDTO.getName());
        materialsToys.setCategory(materialsToysDTO.getCategory());
        materialsToys.setDescription(materialsToysDTO.getDescription());
        materialsToys.setImageUrl(materialsToysDTO.getImageUrl());
        return materialsToys;
    }

    public boolean idExists(final String id) {
        return materialsToysRepository.existsByIdIgnoreCase(id);
    }

}
