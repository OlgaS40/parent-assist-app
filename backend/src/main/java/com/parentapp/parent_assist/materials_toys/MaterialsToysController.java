package com.parentapp.parent_assist.materials_toys;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/materialsToyss", produces = MediaType.APPLICATION_JSON_VALUE)
public class MaterialsToysController {

    private final MaterialsToysService materialsToysService;

    public MaterialsToysController(final MaterialsToysService materialsToysService) {
        this.materialsToysService = materialsToysService;
    }

    @GetMapping
    public ResponseEntity<List<MaterialsToysDTO>> getAllMaterialsToyss() {
        return ResponseEntity.ok(materialsToysService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaterialsToysDTO> getMaterialsToys(@PathVariable final String id) {
        return ResponseEntity.ok(materialsToysService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Void> createMaterialsToys(
            @RequestBody @Valid final MaterialsToysDTO materialsToysDTO,
            final BindingResult bindingResult) throws MethodArgumentNotValidException {
        if (!bindingResult.hasFieldErrors("id") && materialsToysDTO.getId() == null) {
            bindingResult.rejectValue("id", "NotNull");
        }
        if (!bindingResult.hasFieldErrors("id") && materialsToysService.idExists(materialsToysDTO.getId())) {
            bindingResult.rejectValue("id", "Exists.materialsToys.id");
        }
        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(new MethodParameter(
                    this.getClass().getDeclaredMethods()[0], -1), bindingResult);
        }
        materialsToysService.create(materialsToysDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateMaterialsToys(@PathVariable final String id,
            @RequestBody @Valid final MaterialsToysDTO materialsToysDTO) {
        materialsToysService.update(id, materialsToysDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteMaterialsToys(@PathVariable final String id) {
        materialsToysService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
