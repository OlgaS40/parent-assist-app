package com.parentapp.parent_assist.relationships;

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
@RequestMapping(value = "/api/relationshipss", produces = MediaType.APPLICATION_JSON_VALUE)
public class RelationshipsController {

    private final RelationshipsService relationshipsService;

    public RelationshipsController(final RelationshipsService relationshipsService) {
        this.relationshipsService = relationshipsService;
    }

    @GetMapping
    public ResponseEntity<List<RelationshipsDTO>> getAllRelationshipss() {
        return ResponseEntity.ok(relationshipsService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RelationshipsDTO> getRelationships(@PathVariable final String id) {
        return ResponseEntity.ok(relationshipsService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Void> createRelationships(
            @RequestBody @Valid final RelationshipsDTO relationshipsDTO,
            final BindingResult bindingResult) throws MethodArgumentNotValidException {
        if (!bindingResult.hasFieldErrors("id") && relationshipsDTO.getId() == null) {
            bindingResult.rejectValue("id", "NotNull");
        }
        if (!bindingResult.hasFieldErrors("id") && relationshipsService.idExists(relationshipsDTO.getId())) {
            bindingResult.rejectValue("id", "Exists.relationships.id");
        }
        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(new MethodParameter(
                    this.getClass().getDeclaredMethods()[0], -1), bindingResult);
        }
        relationshipsService.create(relationshipsDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateRelationships(@PathVariable final String id,
            @RequestBody @Valid final RelationshipsDTO relationshipsDTO) {
        relationshipsService.update(id, relationshipsDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteRelationships(@PathVariable final String id) {
        relationshipsService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
