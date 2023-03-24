package com.parentapp.material_toy;

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
@RequestMapping(value = "/api/materialsToys", produces = MediaType.APPLICATION_JSON_VALUE)
public class MaterialToyController {

    private final MaterialToyService materialToyService;

    public MaterialToyController(final MaterialToyService materialToyService) {
        this.materialToyService = materialToyService;
    }

    @GetMapping
    public ResponseEntity<List<MaterialToyDTO>> getAllMaterialsToys() {
        return ResponseEntity.ok(materialToyService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaterialToyDTO> getMaterialToy(@PathVariable final String id) {
        return ResponseEntity.ok(materialToyService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Void> createMaterialToy(
            @RequestBody @Valid final MaterialToyDTO materialToyDTO,
            final BindingResult bindingResult) throws MethodArgumentNotValidException {
        if (!bindingResult.hasFieldErrors("id") && materialToyDTO.getId() == null) {
            bindingResult.rejectValue("id", "NotNull");
        }
        if (!bindingResult.hasFieldErrors("id") && materialToyService.idExists(materialToyDTO.getId())) {
            bindingResult.rejectValue("id", "Exists.materialsToys.id");
        }
        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(new MethodParameter(
                    this.getClass().getDeclaredMethods()[0], -1), bindingResult);
        }
        materialToyService.create(materialToyDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateMaterialToy(@PathVariable final String id,
                                                  @RequestBody @Valid final MaterialToyDTO materialToyDTO) {
        materialToyService.update(id, materialToyDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteMaterialToy(@PathVariable final String id) {
        materialToyService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
